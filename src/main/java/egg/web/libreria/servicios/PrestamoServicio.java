//
//package egg.web.libreria.servicios;
//
//import egg.web.libreria.entidades.Libro;
//import egg.web.libreria.entidades.Prestamo;
//import egg.web.libreria.entidades.Usuario;
//import egg.web.libreria.exception.ExceptionServicio;
//import egg.web.libreria.repositorios.LibroRepositorio;
//import egg.web.libreria.repositorios.PrestamoRepo;
//import egg.web.libreria.repositorios.UsuarioRepositorio;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//

//@Service
//public class PrestamoServicio {
//
//    @Autowired
//    private PrestamoRepo prestamoRepositorio;
//
//    @Autowired
//    private LibroRepositorio libroRepositorio;
//
//    @Autowired
//    private UsuarioRepositorio usuarioRepo;
//
//    @Autowired
//    private EnvioMailServicio envioMail;
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public Prestamo nuevoPrestamo(String idUsuario, String idLibro) throws ExceptionServicio {
//
//        if (idUsuario == null || idUsuario.isEmpty()) {
//            throw new ExceptionServicio("El id de usuario no puede ser nulo");
//        }
//        if (idLibro == null || idLibro.isEmpty()) {
//            throw new ExceptionServicio("El id de libro no puede ser nulo");
//        }
//        Libro libro = libroRepositorio.getById(idLibro);
//        Usuario usuario = usuarioRepo.getById(idUsuario);
//
//        Prestamo prestamo = new Prestamo();
//        prestamo.setLibro(libro);
//        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
//        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
//        prestamo.setUsuario(usuario);
//        prestamo.setFechaprestamo(new Date());
//        prestamo.setAlta(true);
//
//        prestamoRepositorio.save(prestamo);
//        try {
////               envioMail.enviar("TU PRESTAMO SE REALIZO CON EXITO", "Libreria Martin", usuario.getEmail());
//        } catch (Exception e) {
//        }
//     
//
//        return prestamo;
//
//    }
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public Prestamo Devolucion(String idPrestamo, String idUsuario) throws ExceptionServicio {
//
//        if (idUsuario == null || idUsuario.isEmpty()) {
//            throw new ExceptionServicio("El id de usuario no puede ser nulo");
//        }
//
//        if (idPrestamo == null || idPrestamo.isEmpty()) {
//            throw new ExceptionServicio("El id del prestamo no puede ser nulo");
//        }
//        String idLibro = prestamoRepositorio.idlibro(idPrestamo);
//
//        Libro libro = libroRepositorio.getById(idLibro);
//        Usuario usuario = usuarioRepo.getById(idUsuario);
//
//        Optional<Prestamo> respuesta = prestamoRepositorio.findById(idPrestamo);
//        if (respuesta.isPresent()) {
//
//            Prestamo prestamo = respuesta.get();
//            prestamo.setLibro(libro);
//            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
//            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
//            prestamo.setUsuario(usuario);
//            prestamo.setFechadevolucion(new Date());
//            prestamo.setAlta(false);
//
//            prestamoRepositorio.save(prestamo);
//            try {
////                  envioMail.enviar("TU DEVOLUCION SE REALIZO CON EXITO", "Libreria Martin", usuario.getEmail());
//            } catch (Exception e) {
//            }
//   
//            return prestamo;
//
//        } else {
//            throw new ExceptionServicio("El Libro no existe");
//        }
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<Prestamo> listaPrestamos(String id) {
//
//        return prestamoRepositorio.buscarPorUSUARIO(id,true);
//    }
//    
//         @Transactional(readOnly = true)
//    public List<Prestamo> listaPrestamos() {
//
//        return prestamoRepositorio.findAll();
//    }
//
//}
