package cinema.exception;

public class TokenIsIncorrectException extends RuntimeException {

    public TokenIsIncorrectException() {
        super("Wrong token!");
    }
}
