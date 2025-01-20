package foro_hub.foro.controller;

import foro_hub.foro.domain.topic.data.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import foro_hub.foro.domain.topic.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<AnswerTopic> registroTopico(@RequestBody @Valid RegisterTopic datos, UriComponentsBuilder uriBuilder) {
        AnswerTopic topico = topicService.registro(datos);
        URI uri = uriBuilder.path("/topicos/{idAdmin}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DataTopic>> listarTopico(@PageableDefault(size = 5,
            sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicService.listadoTopico(paginacion));
    }

    @GetMapping("/curso/{nombreCurso}")
    public ResponseEntity<Page<DataTopic>> listarTopicosPorCurso(@PageableDefault(size = 5,sort = "fechaCreacion", direction = Sort.Direction.ASC)
                                                                         Pageable paginacion, @PathVariable String nombreCurso){
        return ResponseEntity.ok(topicService.listadoTopicosPorCurso(nombreCurso, paginacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailTopic> detallesTopico(@PathVariable Long id){
        return ResponseEntity.ok(topicService.detalleTopico(id));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataTopic> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActTopic datos){
        return ResponseEntity.ok(topicService.actualizar(id,datos));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
