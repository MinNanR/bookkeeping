package site.minnan.bookkeeping.infrastructure.exception;

public class InvalidVerificationCodeException extends Exception {

    private static final long serialVersionUID = 7432951369961908820L;

    public InvalidVerificationCodeException(){
        super();
    }

    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
