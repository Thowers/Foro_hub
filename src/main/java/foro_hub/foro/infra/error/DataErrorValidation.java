package foro_hub.foro.infra.error;

import org.springframework.validation.FieldError;

public record DataErrorValidation(String campo, String error){

    public DataErrorValidation(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
