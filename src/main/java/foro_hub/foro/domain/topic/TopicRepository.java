package foro_hub.foro.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAllByCursoNombre(String cursoNombre, Pageable pageable);

    boolean existsTopicoByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
            select t.status
            from Topico t
            where t.id = :id  
            """)
    boolean retornarStatusTopico (Long id);
}
