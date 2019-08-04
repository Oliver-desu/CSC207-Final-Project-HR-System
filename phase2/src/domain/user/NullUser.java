package domain.user;

import domain.Exceptions.NotCompanyWorkerException;

public class NullUser extends User {
    public NullUser() {
    }

    public boolean isNull() {
        return true;
    }

    @Override
    public String getCompanyId() throws NotCompanyWorkerException {
        throw new NotCompanyWorkerException();
    }
}
