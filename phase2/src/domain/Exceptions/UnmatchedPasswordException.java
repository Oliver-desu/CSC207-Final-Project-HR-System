package domain.Exceptions;

public class UnmatchedPasswordException extends Exception {

    @Override
    public String getMessage() {
        return "Password not matched!";
    }
}
