package foro_hub.foro.domain.topic;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.course.Course;
import foro_hub.foro.domain.course.CourseRepository;
import foro_hub.foro.domain.topic.data.*;
import foro_hub.foro.domain.topic.validations.ValidatorRegisterTopic;
import foro_hub.foro.domain.user.User;
import foro_hub.foro.domain.user.UserRepository;
import foro_hub.foro.domain.user.perfil.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TopicService {

    private final CourseRepository courseRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private List<ValidatorRegisterTopic> validadorDeDuplicados;


    public TopicService(CourseRepository courseRepository, TopicRepository topicRepository, UserRepository userRepository, List<ValidatorRegisterTopic> validadorDeDuplicados) {
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.validadorDeDuplicados = validadorDeDuplicados;
    }

    public AnswerTopic registro(RegisterTopic datos){
        if (!courseRepository.existsById(datos.idCurso())){
            throw new ValidationException("El curso no existe");
        }

        //Validaciones
        validadorDeDuplicados.forEach(v -> v.validar(datos));

        LocalDateTime fechaCreacion = LocalDateTime.now();
        Boolean status = false;
        Integer respuestas = 0;
        Course course = courseRepository.getReferenceById(datos.idCurso());



        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User autor = userRepository.getReferenceById(usuarioAutenticado.getId());
        Topic topic = new Topic(null, datos.titulo(), datos.mensaje(), fechaCreacion, status, autor, course, respuestas);

        topicRepository.save(topic);

        return new AnswerTopic(topic);
    }

    public Page<DataTopic> listadoTopico(Pageable pageable) {
        Page<Topic> topicos = topicRepository.findAll(pageable);

        return topicos.map(DataTopic::new);
    }

    public Page<DataTopic> listadoTopicosPorCurso(String nombre, Pageable pageable) {
        Page<Topic> topicos = topicRepository.findAllByCursoNombre(nombre, pageable);

        return topicos.map(DataTopic::new);
    }

    public DetailTopic detalleTopico(Long idTopico){
        if (!topicRepository.existsById(idTopico)){
            throw new ValidationException("El topico no existe");
        }

        DetailTopic topico = topicRepository.findById(idTopico).map(DetailTopic::new).get();

        return topico;
    }

    public DataTopic actualizar(Long id, ActTopic datos){
        Optional<Topic> verificar = topicRepository.findById(id);

        if (verificar.isPresent()){
            Topic topic = verificar.get();
            topic.actualizarDatosTopico(datos);
            return new DataTopic(topic);
        }
        throw new ValidationException("El topico no existe");
    }

    public void eliminarTopico(Long id){
        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Type.ADMINISTRADOR)){
            throw new ValidationException("El usuario no tiene permisos para eliminar topico");
        }

        if (!topicRepository.existsById(id)){
            throw new ValidationException("El topico no existe");
        }
        topicRepository.deleteById(id);
    }
}
