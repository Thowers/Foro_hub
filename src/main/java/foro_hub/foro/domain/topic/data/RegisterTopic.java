package foro_hub.foro.domain.topic.data;

import jakarta.validation.constraints.NotNull;

public record RegisterTopic(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Long idCurso) {
}
