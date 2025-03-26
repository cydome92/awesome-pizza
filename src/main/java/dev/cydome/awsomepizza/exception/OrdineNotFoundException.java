package dev.cydome.awsomepizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Ordine non trovato.")
public class OrdineNotFoundException extends RuntimeException {

    public OrdineNotFoundException() {
        super();
    }
}
