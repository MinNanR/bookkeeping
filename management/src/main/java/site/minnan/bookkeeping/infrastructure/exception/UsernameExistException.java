package site.minnan.bookkeeping.infrastructure.exception;

public class UsernameExistException extends Exception{

    public UsernameExistException(){
        super();
    }

    public UsernameExistException(String message){
        super(message);
    }
}
