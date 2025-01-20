package foro_hub.foro.domain.answer.Validations;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.answer.data.RegistrarResp;
import foro_hub.foro.domain.topic.TopicRepository;
import org.springframework.stereotype.Component;

@Component("Validar si registra en un topico false")
public class ValidarRespuestaFalse implements ValidatorRegistroRespuesta {

    private final TopicRepository topicRepository;

    public ValidarRespuestaFalse(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public final void validar(RegistrarResp datos){
        if (topicRepository.retornarStatusTopico(datos.idTopico())){
            throw new ValidationException("El topico ya fue respuesto, no puede agregar mas respuestas");
        }
    }
}
