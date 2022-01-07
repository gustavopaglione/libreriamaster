package egg.web.libreria.controladores;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;
import egg.web.libreria.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login_web")
    public String login() {
        return "login_web.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }
    @GetMapping("/lista-libro")
    public String listaLibros() {
        return "lista-libro.html";
    }
    @GetMapping("/lista_autor")
    public String listaAutor() {
        return "lista_autor.html";
    }
    @GetMapping("/lista_editorial")
    public String listaEditor() {
        return "lista_editorial.html";
    }
    
      @PostMapping("/registrar")
    public String registrar(@RequestParam String mail, @RequestParam String nombre,@RequestParam String apellido,@RequestParam String telefono,@RequestParam String clave1,@RequestParam String clave2){
        
        try {
            usuarioServicio.registrarUsuario(mail, nombre, apellido, telefono,clave1,clave2);
        } catch (ExceptionServicio ex) {
            Logger.getLogger(IndexControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "registro.html";
        }
       
        return "index.html";
    }
}
