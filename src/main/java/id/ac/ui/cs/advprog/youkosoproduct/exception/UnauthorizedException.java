package id.ac.ui.cs.advprog.youkosoproduct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Generated;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Generated
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}