package domain.Exceptions;

public class CanNotEditDocumentManagerException extends Exception {

    @Override
    public String getMessage() {
        return "You are not allowed to edit in this stage!";
    }
}
