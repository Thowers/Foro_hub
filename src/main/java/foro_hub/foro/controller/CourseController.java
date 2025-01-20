package foro_hub.foro.controller;

import foro_hub.foro.domain.course.Category;
import foro_hub.foro.domain.course.CourseService;
import foro_hub.foro.domain.course.data.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<DataCurso> crearCurso(@RequestBody @Valid RegCurso regCurso, UriComponentsBuilder builder){
        DataCurso curso = courseService.crearCurso(regCurso);
        URI uri = builder.path("/cursos/{id}").buildAndExpand(curso.id()).toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataCurso> buscarCurso(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.obtenerCurso(id));
    }

    @GetMapping
    public ResponseEntity<Page<ListsCurso>> listaCursos(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(courseService.listaCursos(pageable));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<ListNombreCurso>> listaCursosPorCategoria(@PageableDefault(size = 5) Pageable pageable,
                                                                         @PathVariable Category category) {
        return ResponseEntity.ok(courseService.listarCursosPorCategoria(pageable, category));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarCurso(@PathVariable Long id, @RequestBody @Valid ActCurso actCurso) {
        return ResponseEntity.ok(courseService.actualizarCurso(id, actCurso));
    }
}
