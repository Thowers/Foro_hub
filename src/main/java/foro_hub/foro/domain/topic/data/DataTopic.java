package foro_hub.foro.domain.topic.data;

import foro_hub.foro.domain.course.data.DataCurso;
import foro_hub.foro.domain.topic.Topic;

import java.time.LocalDateTime;

public record DataTopic(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean status,
        DataCurso dataCurso,
        Integer respuestas) {

    public DataTopic(Topic topic) {
        this(topic.getTitulo(), topic.getMensaje(), topic.getFechaCreacion(), topic.getStatus(),
                new DataCurso(topic.getId(), topic.getCourse().getNombre(), topic.getCourse().getCategory()),
                topic.getRespuestas());
    }
}
