package site.minnan.bookkeeping.infrastructure.exception;

public class EntityNotExistException extends Exception {

    public EntityNotExistException() {
        super();
    }

    public EntityNotExistException(String message){
        super(message);
    }
}
