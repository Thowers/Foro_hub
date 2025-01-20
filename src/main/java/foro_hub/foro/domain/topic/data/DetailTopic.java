package foro_hub.foro.domain.topic.data;

import foro_hub.foro.domain.course.data.DataCurso;
import foro_hub.foro.domain.topic.Topic;

import java.time.LocalDateTime;

public record DetailTopic(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean status,
        String autor,
        DataCurso curso,
        Integer respuestas) {

    public DetailTopic(Topic topic) {
        this(topic.getTitulo(), topic.getMensaje(), topic.getFechaCreacion(), topic.getStatus(),
                topic.getAutor().getNombre(),
                new DataCurso(topic.getId(), topic.getCourse().getNombre(), topic.getCourse().getCategory()),
                topic.getRespuestas());
    }
}
