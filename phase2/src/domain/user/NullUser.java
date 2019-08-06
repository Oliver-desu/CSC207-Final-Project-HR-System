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
    /**
     * creat a user with nothing in it
     *
     * @see domain.storage.Storage#getUser(String, UserType)
     * @see domain.storage.UserFactory#createUser(HashMap, UserType)
     */
    public NullUser() {
    }

    /**
     * return true if this user is a null user
     *
     * @return true if this user is a null user
     * @see domain.storage.UserFactory#createUser(HashMap, UserType)
     */
    public boolean isNull() {
        return true;
    }

    /**
     * throw a new NotCompanyWorkerExcepation
     * @return throw a new NotCompanyWorkerExcepation
     * @exception NotCompanyWorkerException represent this user is a nulluser
     * @see   Employee#getFilterMap()
     */
    @Override
    public String getCompanyId() throws NotCompanyWorkerException {
        throw new NotCompanyWorkerException();
    }
}
