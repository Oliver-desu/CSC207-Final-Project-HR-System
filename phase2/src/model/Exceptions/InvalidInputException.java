package model.exceptions;

public class InvalidInputException extends Exception {

    @Override
    public String getMessage() {
        return "You entered an invalid input therefore nothing happened";
    }
}
