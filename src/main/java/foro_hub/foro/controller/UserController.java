package foro_hub.foro.controller;

import foro_hub.foro.domain.user.UserService;
import foro_hub.foro.domain.user.data.CreateUser;
import foro_hub.foro.domain.user.data.ModifyUser;
import foro_hub.foro.domain.user.data.AnswerUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity crearUsuario(@Valid @RequestBody CreateUser createUser, UriComponentsBuilder builder) {
        AnswerUser usuario = userService.registrarUsuario(createUser);
        URI uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity modificarUsuario(@PathVariable Long id, @RequestBody ModifyUser datos) {
        return ResponseEntity.ok(userService.actualizarUsuario(id, datos));
    }

}
