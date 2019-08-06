package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;

import java.util.HashMap;

/**
 * Class {@code NullUser} is created and returned when other types of
 * {@code User} can not be correctly constructed or correctly found.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see domain.storage.UserFactory#createUser(HashMap, UserType)
 * @see domain.storage.Storage#getUser(String, UserType)
 * @since 2019-08-04
 */
public class NullUser extends User {

    public NullUser() {
    }

    /**
     * Override method in {@code User} to indicate that this is a {@code NullUser}.
     *
     * @return true which indicates this is a {@code NullUser}
     * @see User#isNull()
     */
    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String getCompanyId() throws NotCompanyWorkerException {
        throw new NotCompanyWorkerException();
    }
}
