package foro_hub.foro.domain.user.data;

import java.util.Set;

public record ModifyUser(
        String nombre,
        String correo,
        String contrasena,
        Set<String> roles) {
}
