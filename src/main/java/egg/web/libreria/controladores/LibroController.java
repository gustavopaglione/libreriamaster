package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.repositorios.AutorRepositorio;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    LibroServicio libroServicio;
     @Autowired
    AutorServicio autorServicio;
      @Autowired
    EditorialServicio editorialServicio;
    
     @Autowired
    AutorRepositorio autorRepositorio;

    @GetMapping("/lista-libro")
    public String libros(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.put("libros", libros);
        return "lista-libro.html";
    }

    @GetMapping("/libros-baja")
    public String baja(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.put("libros", libros);
        return "libros-baja.html";
    }
 
        
   @GetMapping("/cargar_libro")
    public String formAutor(ModelMap modelo, ModelMap modelo2) throws ExceptionServicio {
        
        modelo.put("autores", autorServicio.listarAutores());
        
        modelo2.put("editoriales", editorialServicio.listarEditoriales());
        
    
        return "cargar_libro.html";
    }

    @PostMapping("/cargar_libro")
    public String agregarLibro(@RequestParam @Nullable MultipartFile archivo, ModelMap modelo, @RequestParam @Nullable Long isbn, @RequestParam @Nullable String titulo, @RequestParam @Nullable Integer anio, @RequestParam @Nullable Integer ejemplares, @RequestParam @Nullable Integer ejemplaresPrestados, @RequestParam @Nullable Boolean alta, @RequestParam @Nullable String nombreAutor, @RequestParam @Nullable String nombreEditorial) throws ExceptionServicio {

        Integer ejemplaresRestantes = ejemplares - ejemplaresPrestados;
        if (alta == null) {
            alta = false;
        }
        try {
            libroServicio.registrarLibro(archivo, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);
        } catch (ExceptionServicio ex) {
            Logger.getLogger(IndexControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
            modelo.put("autores", nombreAutor);
            modelo.put("editoriales",nombreEditorial);
            return "cargar_libro.html";
        }
        modelo.put("titulo", "Libro agregado exitosamente!");
        return "exito.html";
    }

    @GetMapping("/modificar-libro/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo) throws ExceptionServicio {
        Libro libro = libroServicio.buscarLibroPorID(id);
        modelo.addAttribute("libro", libro);
        return "form-mod-libro.html";

    }

    @PostMapping("/modificar-libro/{id}")
    public String modificarLibro(@RequestParam @Nullable MultipartFile archivo, @PathVariable String id, ModelMap modelo, @RequestParam @Nullable Long isbn, @RequestParam @Nullable String titulo, @RequestParam @Nullable Integer anio, @RequestParam @Nullable Integer ejemplares, @RequestParam @Nullable Integer ejemplaresPrestados, @RequestParam @Nullable Boolean alta, @RequestParam @Nullable String nombreAutor, @RequestParam @Nullable String nombreEditorial) {
        Integer ejemplaresRestantes;
        if(ejemplares != null && ejemplaresPrestados != null){
            ejemplaresRestantes = ejemplares - ejemplaresPrestados;
        }else{
            ejemplaresRestantes = 0;
        }
        try {
            if (alta == null) {
                alta = false;
            }else{
                alta = true;
            }
            libroServicio.modificarLibro(archivo, id, alta, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);
            modelo.put("titulo", "Libro modificado exitosamente!");
            return "exito.html";
        } catch (ExceptionServicio ex) {
            Logger.getLogger(IndexControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            modelo.put("titulo", "Error");
            modelo.put("error", ex.getMessage());
            return "fracaso";
        }
    }

    @GetMapping("/eliminar-libro")
    public String eliminarLibro(String id, ModelMap modelo, RedirectAttributes red) {
        try {
            libroServicio.modificarAltaLibro(Boolean.FALSE, id);
            return "libros";
        } catch (Exception ex) {
            Logger.getLogger(IndexControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            modelo.put("titulo", "Error");
            modelo.put("error", ex.getMessage());
            return "fracaso";
        }
    }

    @GetMapping("/revivir-libro")
    public String revivirLibro(String id, ModelMap modelo) {
        try {
            libroServicio.modificarAltaLibro(Boolean.TRUE, id);
            return "libros";
        } catch (Exception ex) {
            Logger.getLogger(IndexControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            modelo.put("titulo", "Error");
            modelo.put("error", ex.getMessage());
            return "fracaso";
        }
    }
}