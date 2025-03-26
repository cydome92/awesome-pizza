package dev.cydome.awesomepizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pizza non trovata.")
public class MissingPizzaException extends RuntimeException {

    public MissingPizzaException() {
        super();
    }
}
