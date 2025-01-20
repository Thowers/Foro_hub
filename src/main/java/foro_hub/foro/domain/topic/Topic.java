package foro_hub.foro.domain.topic;

import foro_hub.foro.domain.course.Course;
import foro_hub.foro.domain.topic.data.ActTopic;
import foro_hub.foro.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topicos")
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor")
    private User autor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso")
    private Course course;
    private Integer respuestas;

    public void actualizarDatosTopico(ActTopic actTopic) {
        if (actTopic.titulo() != null) this.titulo = actTopic.titulo();
        if (actTopic.mensaje() != null) this.mensaje = actTopic.mensaje();
    }

    public void aumentarRespuestas() {
        this.respuestas++;
    }
}
