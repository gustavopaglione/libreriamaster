
package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Usuario;
import egg.web.libreria.servicios.EnvioMailServicio;
import egg.web.libreria.servicios.FotoServicio;
import egg.web.libreria.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller

@RequestMapping("/")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private FotoServicio fotoservicio;

    @Autowired
    private EnvioMailServicio envioMail;

   

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
    @GetMapping("/inicio/editar-perfil/{id}")
    public String editarperfil(HttpSession session, @PathVariable String id, ModelMap modelo) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/inicio";
        }

        Usuario usuario = usuarioServicio.buscarPorId(id);

        modelo.addAttribute("usuario", usuario);

        return "eidtarPerfil";

    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
    @PostMapping("/inicio/editar-perfil")
    public String registro(HttpSession session, @RequestParam String id, ModelMap modelo, MultipartFile archivo, @RequestParam String mail, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String clave, @RequestParam String clave2) {
        Usuario usuario = null;

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.modificarUsuario(id, archivo, mail, nombre, apellido, telefono, apellido, apellido);
            return "redirect:/inicio";

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "eidtarPerfil";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/usuarios")
    public String usuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listaUsuarios();
        modelo.addAttribute("usuario", usuarios);

        return "listaUsuarios";
    }

}

