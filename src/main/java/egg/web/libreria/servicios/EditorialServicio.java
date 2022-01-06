package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.exception.ExceptionServicio;
import egg.web.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public void validar(String nombre) throws ExceptionServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ExceptionServicio("Nombre de la editorial vacio o nulo");
        }
    }

    @Transactional
    public void registrarEditorial(String nombre, Boolean alta) throws ExceptionServicio {
        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(alta);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String id, String nombre, Boolean alta) throws ExceptionServicio {
        validar(nombre);
        Optional<Editorial> res = editorialRepositorio.findById(id);
        if (res.isPresent()) {
            Editorial editorial = res.get();

            editorial.setNombre(nombre);
            editorial.setAlta(alta);

            editorialRepositorio.save(editorial);
        } else {
            throw new ExceptionServicio("No se encontro editorial con ese ID");
        }
    }

    @Transactional
    public void altaEditorial(String id, Boolean alta) throws ExceptionServicio {
        Optional<Editorial> res = editorialRepositorio.findById(id);
        if (res.isPresent()) {
            Editorial editorial = res.get();

            editorial.setAlta(alta);

            editorialRepositorio.save(editorial);
        } else {
            throw new ExceptionServicio("No se encontro editorial con ese ID");
        }
    }

    public Editorial buscarEditorialPorNombre(String nombre) throws ExceptionServicio {
        validar(nombre);
        return editorialRepositorio.BuscarEditorialPorNombre(nombre);
    }

    public List<Editorial> listarEditoriales() {
        return editorialRepositorio.findAll();
    }

    public Editorial buscarEditorialPorID(String id) {
        return editorialRepositorio.getById(id);
    }
}