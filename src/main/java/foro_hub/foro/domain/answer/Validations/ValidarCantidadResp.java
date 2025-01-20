package foro_hub.foro.domain.answer.Validations;

import foro_hub.foro.domain.ValidationException;
import foro_hub.foro.domain.answer.AnswerRepository;
import foro_hub.foro.domain.answer.data.RegistrarResp;
import org.springframework.stereotype.Component;

@Component("Validar si ttiene mas de 2 respuestas el mismo topico")
public class ValidarCantidadResp implements ValidatorRegistroRespuesta {

    private AnswerRepository answerRepository;

    public ValidarCantidadResp(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void validar(RegistrarResp datos) {
        int cantidadDeRespuestas = answerRepository.cantidadDeRespuestasEnUnTopico(datos.idTopico());

        if (cantidadDeRespuestas > 2){
            throw new ValidationException("No puedes agregar mas de dos respuestas al mismo topico.");
        }

    }
}
