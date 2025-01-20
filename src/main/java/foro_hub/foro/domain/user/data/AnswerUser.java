package foro_hub.foro.domain.user.data;

import foro_hub.foro.domain.user.User;
import foro_hub.foro.domain.user.perfil.Profile;

import java.util.Set;

public record AnswerUser(
        Long id,
        String nombre,
        String correo,
        Set<Profile> roles) {

    public AnswerUser(User user){
        this(user.getId(), user.getNombre(), user.getCorreo(), user.getRoles());
    }
}
