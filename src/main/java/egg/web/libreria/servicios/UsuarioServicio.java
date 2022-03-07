package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Foto;
import egg.web.libreria.entidades.Usuario;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void registrarUsuario(MultipartFile archivo, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ExceptionServicio {

        validar(email, nombre, apellido, telefono, password1, password2);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        String encriptada = new BCryptPasswordEncoder().encode(password1);
        usuario.setPassword(encriptada);
        
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

        usuarioRepo.save(usuario);
    }

    @Transactional
    public void modificarUsuario(String id, MultipartFile archivo, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ExceptionServicio {

        validar(email, nombre, apellido, telefono, password1, password2);

        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setTelefono(telefono);
            String encriptada = new BCryptPasswordEncoder().encode(password1);
            usuario.setPassword(encriptada);
            
            Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

            usuarioRepo.save(usuario);
        } else {
            throw new ExceptionServicio("No se encontró el usuario con id= " + id);
        }
    }
        @Transactional
    
        public void mostrarUsuario(String id, MultipartFile archivo, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ExceptionServicio {

        validar(email, nombre, apellido, telefono, password1, password2);

        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setTelefono(telefono);
            String encriptada = new BCryptPasswordEncoder().encode(password1);
            usuario.setPassword(encriptada);
            
            Foto foto = fotoServicio.mostrar(id, archivo);
        usuario.setFoto(foto);

            
        } else {
            throw new ExceptionServicio("No se encontró el usuario con id= " + id);
        }
    }

    private void validar(String email, String nombre, String apellido, String telefono, String password1, String password2) throws ExceptionServicio {

        if (email == null || email.isEmpty()) {
            throw new ExceptionServicio("Email sin especificar");
        }

        if (usuarioRepo.findByEmail(email) != null) {
            throw new ExceptionServicio("Este email ya se encuentra en uso");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ExceptionServicio("Nombre sin especificar");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ExceptionServicio("Apellido sin especificar");
        }

        if (telefono == null || telefono.isEmpty()) {
            throw new ExceptionServicio("Telefono sin especificar");
        }

        if (password1 == null || password1.isEmpty()) {
            throw new ExceptionServicio("Contraseña sin especificar");
        }

        if (!password1.equals(password2)) {
            throw new ExceptionServicio("No coinciden las contraseñas");
        }

    }
      
        @Transactional(readOnly = true)
    public Usuario buscarPorId(String id) {

        return usuarioRepo.getById(id);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPormail(String mail) {

        return usuarioRepo.findByEmail(mail);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listaUsuarios() {

        return usuarioRepo.findAll();
    }
    //SEGURIDAD----------------------------------------

       @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(mail);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            GrantedAuthority p2 = new SimpleGrantedAuthority("ALTA_" + usuario.getAlta());
            permisos.add(p1);
            permisos.add(p2);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);

            return user;

        } else {
            return null;
        }

    }

   
}
