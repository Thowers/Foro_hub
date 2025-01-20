package foro_hub.foro.controller;

import foro_hub.foro.domain.answer.AnswerService;
import foro_hub.foro.domain.answer.data.ActResp;
import foro_hub.foro.domain.answer.data.DataResp;
import foro_hub.foro.domain.answer.data.RegistrarResp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<DataResp> RegistarRespuesta(@RequestBody @Valid RegistrarResp registrarResp, UriComponentsBuilder builder) {
        DataResp respuesta = answerService.registrarRespuesta(registrarResp);
        URI uri = builder.path("/respuestas/{idAdmin}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Page<DataResp>> obtenerRespuestaPorTopico(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(answerService.listarRespuestasPorTopico(id, pageable));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataResp> modificarRespuesta(@RequestBody @Valid ActResp mensaje, @PathVariable Long id){
        return ResponseEntity.ok(answerService.modificarRespuesa(id, mensaje));
    }

    @PutMapping("/solucion/{id}")
    @Transactional
    public ResponseEntity<DataResp> statusRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(answerService.statusRespuesta(id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        answerService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

}
