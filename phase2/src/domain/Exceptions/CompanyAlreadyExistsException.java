package domain.Exceptions;

public class CompanyAlreadyExistsException extends Exception {

    @Override
    public String getMessage() {
        return "Company name already exists!";
    }
}
