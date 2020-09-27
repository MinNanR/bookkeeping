package site.minnan.bookkeeping.infrastructure.exception;

public class EntityExistException extends Exception{

    public EntityExistException(){
        super();
    }

    public EntityExistException(String message){
        super(message);
    }
}
