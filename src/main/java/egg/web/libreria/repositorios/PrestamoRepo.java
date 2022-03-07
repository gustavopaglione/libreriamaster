
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, String>{
    
      @Query("SELECT a FROM Prestamo a WHERE a.usuario.id = :id AND a.alta = :alta")
    public List<Prestamo> buscarPorUSUARIO(@Param("id") String id,@Param("alta") Boolean alta);
    
       @Query("SELECT a.libro.id FROM Prestamo a WHERE a.id = :id")
    public String idlibro(@Param("id") String id);
}
