package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotEmployeeException;

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
     * Constructor for {@code NullUser}.
     * @see domain.storage.UserFactory#createUser(HashMap, UserType)
     */
    public NullUser() {
    }

    /**
     * Return true because this is a {@code NullUser}.
     * @return true
     * @see User#isNull()
     */
    public boolean isNull() {
        return true;
    }

    /**
     * Throw a {@code NotEmployeeException} because {@code NullUser} does not work
     * for company.
     * @return nothing since it always throw {@code NotEmployeeException}
     * @exception NotEmployeeException this is not an employee
     * @see User#getCompanyId()
     */
    @Override
    public String getCompanyId() throws NotEmployeeException {
        throw new NotEmployeeException();
    }
}
