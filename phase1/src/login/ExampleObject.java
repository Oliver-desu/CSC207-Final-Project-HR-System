package login;

import java.awt.event.ActionListener;

public class ExampleObject implements SearchObject {
    @Override
    public String getSearchValue1() {
        return "Some job";
    }

    @Override
    public String getSearchValue2() {
        return "Some date";
    }

    @Override
    public String getSearchValue3() {
        return "Some date";
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }
}
