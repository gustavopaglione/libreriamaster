
package egg.web.libreria.servicios;


import egg.web.libreria.entidades.Autor;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    public void validar(String nombre) throws ExceptionServicio{
        
        if(nombre == null || nombre.isEmpty()){
            throw new ExceptionServicio("Nombre del autor vacio o nulo");
        }
    }
    
    @Transactional  
    public void registrarAutor(String nombre, Boolean alta) throws ExceptionServicio{
        validar(nombre);
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);
        
        autorRepositorio.save(autor);
    }
    @Transactional 
    public void modificarAutor(String id, String nombre, Boolean alta) throws ExceptionServicio{
        validar(nombre);
        Optional<Autor> res = autorRepositorio.findById(id);
        if(res.isPresent()){
            Autor autor = res.get();
            
            autor.setNombre(nombre);
            autor.setAlta(alta);
            
            autorRepositorio.save(autor);
        }else{
            throw new ExceptionServicio("No se encontro autor con ese ID");
        }
    }
    @Transactional 
    public void altaAutor(String id, Boolean alta) throws ExceptionServicio{
        Optional<Autor> res = autorRepositorio.findById(id);
        if(res.isPresent()){
            Autor autor = res.get();
            
            autor.setAlta(alta);
            
            autorRepositorio.save(autor);
        }else{
            throw new ExceptionServicio("No se encontro autor con ese ID");
        }
    }
    
    public Autor buscarAutorPorNombre(String nombre) throws ExceptionServicio{
        validar(nombre);
        return autorRepositorio.BuscarAutorPorNombre(nombre);
    }
    public Autor buscarAutorPorID(String id) throws ExceptionServicio{
        return autorRepositorio.getById(id);
    }
    
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }
    
} 

