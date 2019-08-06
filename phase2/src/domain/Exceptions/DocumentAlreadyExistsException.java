package domain.Exceptions;

public class DocumentAlreadyExistsException extends Exception {

    @Override
    public String getMessage() {
        return "Document already exists!";
    }
}
