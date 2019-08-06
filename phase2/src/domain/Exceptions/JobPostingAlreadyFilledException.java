package domain.Exceptions;

public class JobPostingAlreadyFilledException extends Exception {

    @Override
    public String getMessage() {
        return "This job posting has already been filled!";
    }
}
