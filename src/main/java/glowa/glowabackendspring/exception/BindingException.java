package glowa.glowabackendspring.exception;

public class BindingException extends RuntimeException{
    public BindingException() {
        super();
    }

    public BindingException(String message) {
        super(message);
    }
}
