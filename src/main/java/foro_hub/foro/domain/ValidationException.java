package foro_hub.foro.domain;

public class ValidationException extends RuntimeException {

    public ValidationException(String mensaje) {
        super(mensaje);
    }
}
