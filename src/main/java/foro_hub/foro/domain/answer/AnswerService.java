package foro_hub.foro.domain.answer;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.answer.Validations.ValidatorRegistroRespuesta;
import foro_hub.foro.domain.answer.data.ActResp;
import foro_hub.foro.domain.answer.data.DataResp;
import foro_hub.foro.domain.answer.data.RegistrarResp;
import foro_hub.foro.domain.topic.Topic;
import foro_hub.foro.domain.topic.TopicRepository;
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
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final List<ValidatorRegistroRespuesta> validatorRegisterAnswer;

    public AnswerService(AnswerRepository answerRepository, TopicRepository topicRepository, UserRepository userRepository, List<ValidatorRegistroRespuesta> validatorRegisterAnswer) {
        this.answerRepository = answerRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.validatorRegisterAnswer = validatorRegisterAnswer;
    }

    public DataResp registrarRespuesta(RegistrarResp datos) {
        if (!topicRepository.existsById(datos.idTopico())) {
            throw new ValidationException("El topic no existe");
        }

        System.out.println("Entrando aqui");

        //validaciones
        validatorRegisterAnswer.forEach(v -> v.validar(datos));

        Topic topic = topicRepository.getById(datos.idTopico());
        topic.aumentarRespuestas();
        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User autor = userRepository.getReferenceById(usuarioAutenticado.getId());
        LocalDateTime fechaCreacion = LocalDateTime.now();
        Boolean status = false;

        Answer respuesta = new Answer(null, datos.mensaje(), topic, fechaCreacion, autor, status);

        answerRepository.save(respuesta);

        return new DataResp(respuesta);
    }

    public Page<DataResp> listarRespuestasPorTopico(Long id, Pageable page) {
        if (!topicRepository.existsById(id)) {
            throw new ValidationException("El topico no existe");
        }

        Page<Answer> respuesta = answerRepository.findByTopicoId(id, page);

        return respuesta.map(DataResp::new);
    }

    public DataResp modificarRespuesa(Long id, ActResp datos) {
        Optional<Answer> buscar = answerRepository.findById(id);
        if (buscar.isEmpty()) {
            throw new ValidationException("El respuesta no existe");
        }
        Answer respuesta = buscar.get();
        if (datos.mensaje()!= null) respuesta.setMensaje(datos.mensaje());
        return new DataResp(respuesta);
    }

    public DataResp statusRespuesta(Long id) {
        Optional<Answer> buscar = answerRepository.findById(id);
        if (buscar.isEmpty()) {
            throw new ValidationException("El respuesta no existe");
        }
        Answer respuesta = buscar.get();
        respuesta.setSolucion(true);
        Long topicoId = answerRepository.buscarTopicoPorId(id);
        Topic topic = topicRepository.getReferenceById(topicoId);
        topic.setStatus(true);
        return new DataResp(respuesta);
    }

    public void eliminarRespuesta(Long id) {
        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Type.ADMINISTRADOR)){
            throw new ValidationException("El usuario no tiene permisos para eliminar respuesta");
        }

        if (!answerRepository.existsById(id)) {
            throw new ValidationException("El respuesta no existe");
        }
        answerRepository.deleteById(id);
    }

}
