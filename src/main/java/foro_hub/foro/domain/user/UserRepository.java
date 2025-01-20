package foro_hub.foro.domain.user;

import foro_hub.foro.domain.user.perfil.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByNombre(String nombre);

    boolean existsByIdAndRolesNombre(Long id, Type type);

}
