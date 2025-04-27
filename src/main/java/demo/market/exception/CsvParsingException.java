package demo.market.exception;

public class CsvParsingException extends RuntimeException {

    public CsvParsingException(String message) {
        super(message);
    }

    public CsvParsingException(Throwable cause) {
        super(cause);
    }
}
