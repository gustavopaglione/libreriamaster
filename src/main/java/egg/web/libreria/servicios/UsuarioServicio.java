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
    public List<Usuario> listarUsuario() {
        return usuarioRepo.findAll();
    }
    //SEGURIDAD----------------------------------------

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ADMIN"));
       
            UserDetails userDet = new User(usuario.getEmail(),usuario.getPassword(),usuario.getPassword(), roles);
            
            return userDet;
        
    }

   
}
