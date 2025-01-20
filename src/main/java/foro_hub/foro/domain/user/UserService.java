package foro_hub.foro.domain.user;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.answer.AnswerRepository;
import foro_hub.foro.domain.topic.TopicRepository;
import foro_hub.foro.domain.user.data.CreateUser;
import foro_hub.foro.domain.user.data.ModifyUser;
import foro_hub.foro.domain.user.data.AnswerUser;
import foro_hub.foro.domain.user.perfil.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TopicRepository topicRepository, AnswerRepository answerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void verificarRol(String permiso) {
        var usuarioAutenticado = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Type.ADMINISTRADOR)){
            throw new ValidationException("El usuario no tiene permisos para " + permiso);
        }
    }

    public AnswerUser registrarUsuario(CreateUser datos) {
        verificarRol("registrar user");
        User user = new User(datos);
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        userRepository.save(user);
        return new AnswerUser(user);
    }

    public AnswerUser actualizarUsuario(Long id, ModifyUser datos) {
        verificarRol("actualizar usuario");
        Optional<User> usuario = userRepository.findById(id);
        if (usuario.isEmpty()){
            throw new ValidationException("El usuario no existe");
        }
        User userActualizado = usuario.get();
        userActualizado.actualizardatos(datos, passwordEncoder);
        return new AnswerUser(userActualizado);
    }


}
