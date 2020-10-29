package site.minnan.bookkeeping.infrastructure.exception;

public class InvalidVerificationCodeException extends RuntimeException{

    public InvalidVerificationCodeException(String message){
        super(message);
    }

    public InvalidVerificationCodeException(){
        super();
    }
}
