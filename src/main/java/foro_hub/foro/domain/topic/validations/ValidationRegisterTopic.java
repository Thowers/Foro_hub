package foro_hub.foro.domain.topic.validations;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.topic.TopicRepository;
import foro_hub.foro.domain.topic.data.RegisterTopic;
import org.springframework.stereotype.Component;

@Component
public class ValidationRegisterTopic implements ValidatorRegisterTopic {

    private TopicRepository topicRepository;

    public ValidationRegisterTopic(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void validar(RegisterTopic datos) {
        if (topicRepository.existsTopicoByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidationException("Topic ya existe");
        }
    }
}
