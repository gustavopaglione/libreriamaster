package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Foto;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.repositorios.AutorRepositorio;
import egg.web.libreria.repositorios.EditorialRepositorio;
import egg.web.libreria.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {

       @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private FotoServicio fotoServicio;

    public void validarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws ExceptionServicio {
        Autor autor = autorRepositorio.BuscarAutorPorNombre(nombreAutor);
        Editorial editorial = editorialRepositorio.BuscarEditorialPorNombre(nombreEditorial);

        if (autor == null || autor.getNombre().isEmpty()) {
            throw new ExceptionServicio("Nombre de autor erroneo o sin datos asociados");
        }
        if (editorial == null || editorial.getNombre().isEmpty()) {
            throw new ExceptionServicio("Nombre de editorial erroneo o sin datos asociados");
        }

        if (isbn == null || isbn <= 0) {
            throw new ExceptionServicio("Error en ISBN");
        }
        if (anio == null) {
            throw new ExceptionServicio("Año nulo o no valido");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new ExceptionServicio("Nombre de libro vacio o no ingresado");
        }

        if (ejemplares == null || ejemplares < 0) {
            throw new ExceptionServicio("Valor incorrecto en los ejemplares");
        }

        if (ejemplaresPrestados == null || ejemplaresPrestados < 0 || ejemplaresPrestados > ejemplares) {
            throw new ExceptionServicio("Error en el numero de ejemplares prestados");
        }

        if (ejemplaresRestantes == null || ejemplaresRestantes < 0 || ejemplaresRestantes > ejemplares) {
            throw new ExceptionServicio("Error en el numero de ejemplares restantes");
        }
    }

    @Transactional
    public void registrarLibro(MultipartFile archivo, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws ExceptionServicio {
        Autor autor;
        if (autorServicio.buscarAutorPorNombre(nombreAutor) != null) {
            autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        } else {
            autorServicio.registrarAutor(nombreAutor, Boolean.TRUE);
            autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        }

        Editorial editorial;
        if (editorialServicio.buscarEditorialPorNombre(nombreEditorial) != null) {
            editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        } else {
            editorialServicio.registrarEditorial(nombreEditorial, Boolean.TRUE);
            editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        }

        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);

        Libro libro = new Libro();
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setAlta(Boolean.TRUE);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        
        Foto foto = fotoServicio.guardar(archivo);
        libro.setFoto(foto);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(MultipartFile archivo, String id, Boolean alta, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws ExceptionServicio {
        Autor autor;
        if (autorServicio.buscarAutorPorNombre(nombreAutor) != null) {
            autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        } else {
            autorServicio.registrarAutor(nombreAutor, Boolean.TRUE);
            autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        }

        Editorial editorial;
        if (editorialServicio.buscarEditorialPorNombre(nombreEditorial) != null) {
            editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        } else {
            editorialServicio.registrarEditorial(nombreEditorial, Boolean.TRUE);
            editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        }

        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);
        Libro libro = libroRepositorio.getById(id);

        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setAlta(alta);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        
        String idFoto = null;
        if (libro.getFoto() != null) {
            idFoto = libro.getFoto().getId();
        }
        Foto foto = fotoServicio.actualizar(idFoto, archivo);
        libro.setFoto(foto);

        libroRepositorio.save(libro);
//        libroRepositorio.modLibro(id, alta, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);

    }

    @Transactional
    public void modificarAltaLibro(Boolean alta, String id) throws ExceptionServicio {
        try {
            Libro libro = libroRepositorio.getById(id);
            libro.setAlta(alta);

            libroRepositorio.save(libro);
        } catch (Exception e) {
            throw new ExceptionServicio("Error al modificar el alta del libro");
        }
    }

//    public Libro buscarLibroPorTitulo(String titulo) throws ExceptionServicio {
//        if (titulo != null || !titulo.isEmpty()) {
//            return libroRepositorio.buscarLibroPorTitulo(titulo);
//        } else {
//            throw new ExceptionServicio("El titulo a buscar es nulo o vacio");
//        }
//    }
//
//    public Libro buscarLibroPorISBN(Long isbn) throws ExceptionServicio {
//        if (isbn != null || isbn > 0) {
//            return libroRepositorio.buscarLibroPorISBN(isbn);
//        } else {
//            throw new ExceptionServicio("El isbn a buscar es nulo o vacio");
//        }
//    }

    public Libro buscarLibroPorID(String id) throws ExceptionServicio {
        if (id != null || !id.isEmpty()) {
            return libroRepositorio.getById(id);
        } else {
            throw new ExceptionServicio("El id a buscar es nulo o vacio");
        }
    }

//    public ArrayList<Libro> listarLibrosPorAnio(Integer anio) {
//        return libroRepositorio.listarLibrosPorAnio(anio);
//    }
//
//    public ArrayList<Libro> listarLibrosPorAutor(String nombreAutor) {
//        Autor autor = autorRepositorio.BuscarAutorPorNombre(nombreAutor);
//        return libroRepositorio.listarLibrosPorAutor(autor.getId());
//    }

//    public ArrayList<Libro> listarLibrosPorEditorial(String nombreEditorial) {
//        Editorial editorial = editorialRepositorio.BuscarEditorialPorNombre(nombreEditorial);
//        return libroRepositorio.listarLibrosPorEditorial(editorial.getId());
//    }

    public List<Libro> listarLibros() {

        return libroRepositorio.findAll();
    }

//    @Transactional
//    public void modificarLibroPorTitulo(Boolean alta, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws ErrorServicio {
//        Libro libro = buscarLibroPorTitulo(titulo);
//        modificarLibro(libro.getId(), alta, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);
//    }
}