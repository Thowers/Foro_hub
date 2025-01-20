package foro_hub.foro.domain.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findByTopicoId(Long id, Pageable page);

    @Query("""
            SELECT r.topic.id 
            FROM Respuesta r
            WHERE r.id = :id
            """)
    Long buscarTopicoPorId(Long id);

    @Query("""
            select count(r.id)
            from Respuesta r
            where r.topic.id = :id
            """)
    int cantidadDeRespuestasEnUnTopico(Long id);
}
