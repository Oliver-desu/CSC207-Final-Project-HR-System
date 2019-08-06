package domain.Exceptions;

public class UserAlreadyExistsException extends Exception {

    @Override
    public String getMessage() {
        return "Username has already been used by other users!";
    }
}
