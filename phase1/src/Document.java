import login.SearchObject;

import java.awt.event.ActionListener;

public class Document implements SearchObject {
    public  Document(){System.out.println("this is document");}

    @Override
    public ActionListener getSelectAction() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue1() {
        return null;
    }
}
