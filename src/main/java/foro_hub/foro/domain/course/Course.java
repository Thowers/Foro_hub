package foro_hub.foro.domain.course;

import foro_hub.foro.domain.course.data.ActCurso;
import foro_hub.foro.domain.course.data.RegCurso;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "course")
@Table(name = "courses")
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Course(RegCurso regCurso) {
        this.nombre = regCurso.nombre();
        this.category = regCurso.category();
    }

    public final void actualizar(ActCurso actCurso) {
        if (actCurso.nombre() != null) this.nombre = actCurso.nombre();
        if (actCurso.category() != null) this.category = actCurso.category();
    }
}
