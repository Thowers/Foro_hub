package foro_hub.foro.domain.course.data;

import foro_hub.foro.domain.course.Category;
import jakarta.validation.constraints.NotNull;

public record RegCurso(
        @NotNull
        String nombre,
        @NotNull
        Category category
){
}
