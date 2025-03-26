package dev.cydome.awsomepizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Ordine in stato già completato.")
public class OrdineCompletatoException extends RuntimeException {

    public OrdineCompletatoException() {
        super();
    }
}
