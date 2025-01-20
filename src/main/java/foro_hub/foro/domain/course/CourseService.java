package foro_hub.foro.domain.course;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.course.data.*;
import foro_hub.foro.domain.user.User;
import foro_hub.foro.domain.user.UserRepository;
import foro_hub.foro.domain.user.perfil.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    private CourseRepository courseRepository;

    private UserRepository userRepository;

    public final void verificarRol() {
        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Type.ADMINISTRADOR)){
            throw new ValidationException("El usuario no tiene permisos para registrar");
        }
    }

    public final DataCurso crearCurso(RegCurso datos){
        verificarRol();
        Course course = new Course(datos);
        courseRepository.save(course);
        return new DataCurso(course);
    }

    public final DataCurso obtenerCurso(Long id){
        if(!courseRepository.existsById(id)){
            throw new ValidationException("El curso no existe");
        }
        return courseRepository.findById(id).map(DataCurso::new).get();
    }

    public final Page<ListsCurso> listaCursos(Pageable pageable){
        Page<Course> cursos = courseRepository.findAll(pageable);
        return cursos.map(ListsCurso::new);
    }

    public final Page<ListNombreCurso> listarCursosPorCategoria(Pageable pageable, Category category){
        Page<Course> cursos = courseRepository.findByCategoria(category, pageable);
        return cursos.map(ListNombreCurso::new);
    }

    public final DataCurso actualizarCurso(Long id, ActCurso actCurso){
        verificarRol();

        if(!courseRepository.existsById(id)){
            throw new ValidationException("El curso no existe");
        }
        Course course = courseRepository.getReferenceById(id);
        course.actualizar(actCurso);
        return new DataCurso(course);
    }
}
