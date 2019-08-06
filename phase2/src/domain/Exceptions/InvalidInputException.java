package domain.Exceptions;

public class InvalidInputException extends Exception {

    @Override
    public String getMessage() {
        return "You entered an invalid input therefore nothing happened";
    }
}
