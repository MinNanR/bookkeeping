package site.minnan.bookkeeping.infrastructure.exception;

public class EntityNotExistException extends RuntimeException {

    private static final long serialVersionUID = 774813947433229131L;

    public EntityNotExistException() {
        super();
    }

    public EntityNotExistException(String message){
        super(message);
    }
}
