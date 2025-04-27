package demo.market.exception;

public class InvalidCliArgumentException extends RuntimeException{

    public InvalidCliArgumentException(String message) {
        super(message);
    }
}
