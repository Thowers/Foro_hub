package foro_hub.foro.domain.user.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateUser(
        @Email
        @NotNull
        String email,
        @NotNull
        String nombre,
        @NotNull
        String contrasena,
        @NotNull
        Set<String> roles
) {
}
