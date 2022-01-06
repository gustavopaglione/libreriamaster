package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
      @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial BuscarEditorialPorNombre(@Param("nombre")String nombre);
    
    
}
