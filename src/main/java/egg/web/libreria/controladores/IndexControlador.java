package egg.web.libreria.controladores;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroServicio libroServicio;

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
}
