package domain.Exceptions;

public class WrongApplicationStatusException extends Exception {

    @Override
    public String getMessage() {
        return "This application is not pending!";
    }
}
