package site.minnan.bookkeeping.infrastructure.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidVerificationCodeException extends BadCredentialsException {

    private static final long serialVersionUID = 1047297253005547795L;

    public InvalidVerificationCodeException(String message){
        super(message);
    }

}
