package foro_hub.foro.domain.course.data;

import foro_hub.foro.domain.course.Category;
import foro_hub.foro.domain.course.Course;

public record DataCurso(
        Long id,
        String nombre,
        Category category) {

    public DataCurso(Course course){
        this(course.getId(), course.getNombre(), course.getCategory());
    }
}
