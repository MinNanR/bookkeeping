package site.minnan.bookkeeping.infrastructure.exception;

public class EntityAlreadyExistException extends RuntimeException{

    private static final long serialVersionUID = 7764000156803646497L;

    public EntityAlreadyExistException(){
        super();
    }

    public EntityAlreadyExistException(String message){
        super(message);
    }
}
