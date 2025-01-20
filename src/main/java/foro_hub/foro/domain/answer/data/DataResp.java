package foro_hub.foro.domain.answer.data;

import foro_hub.foro.domain.answer.Answer;
import java.time.LocalDateTime;

public record DataResp(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,
        Boolean solucion) {

    public DataResp(Answer respuesta) {
        this(respuesta.getId() , respuesta.getMensaje(), respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(), respuesta.getSolucion());
    }
}
