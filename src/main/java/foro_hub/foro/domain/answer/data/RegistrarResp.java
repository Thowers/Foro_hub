package foro_hub.foro.domain.answer.data;

import jakarta.validation.constraints.NotNull;

public record RegistrarResp(
        @NotNull
        String mensaje,
        @NotNull
        Long idTopico) {
}
