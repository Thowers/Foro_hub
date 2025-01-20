package foro_hub.foro.controller;


import foro_hub.foro.domain.user.User;
import foro_hub.foro.domain.user.data.DataAuth;
import foro_hub.foro.infra.security.JWTToken;
import foro_hub.foro.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Operaciones de login")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public final ResponseEntity<JWTToken> autenticarUsuario(@RequestBody @Valid DataAuth dataAuthUsuario){
        Authentication authcatoken = new UsernamePasswordAuthenticationToken(
                dataAuthUsuario.nombre(), dataAuthUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authcatoken);
        var JWtoken = tokenService.generarToken((User) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JWTToken(JWtoken));
    }

}
