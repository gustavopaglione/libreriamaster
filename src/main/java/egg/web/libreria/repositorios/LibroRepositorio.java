
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Libro;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
   @Query("SELECT l FROM Libro l WHERE titulo = :titulo")
    public Libro buscarLibroPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE ISBN = :isbn")
    public Libro buscarLibroPorISBN(@Param("isbn") long isbn);

    @Query("SELECT l FROM Libro l WHERE anio = :anio")
    public ArrayList<Libro> listarLibrosPorAnio(@Param("anio") Integer anio);

    @Query("SELECT l FROM Libro l WHERE editorial.id = :id")
    public ArrayList<Libro> listarLibrosPorEditorial(@Param("id") String id);

    @Query("SELECT l FROM Libro l WHERE autor.id = :id")
    public ArrayList<Libro> listarLibrosPorAutor(@Param("id") String id);
    
    
}
