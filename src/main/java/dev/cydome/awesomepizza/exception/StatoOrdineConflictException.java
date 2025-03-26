package dev.cydome.awesomepizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Nuovo stato ordine non coerente.")
public class StatoOrdineConflictException extends RuntimeException {

    public StatoOrdineConflictException() {
        super();
    }
}
