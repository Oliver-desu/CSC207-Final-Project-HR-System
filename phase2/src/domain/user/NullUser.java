package domain.user;

import java.util.HashMap;

public class NullUser extends User {
    public NullUser() {
        super(new HashMap<>());
    }

    public boolean isNull() {
        return true;
    }
}
