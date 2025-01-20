package foro_hub.foro.infra.security;

import foro_hub.foro.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AutenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public final UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        return userRepository.findByNombre(nombre);
    }
}