package domain.Exceptions;

public class CompanyDoesNotExistException extends Exception {

    @Override
    public String getMessage() {
        return "Company does not exists!";
    }
}
