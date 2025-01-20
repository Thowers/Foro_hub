package foro_hub.foro.domain.course.data;

import foro_hub.foro.domain.course.Course;

public record ListNombreCurso(
        long id,
        String nombre) {

    public ListNombreCurso(Course course){
        this(course.getId(), course.getNombre());
    }
}
