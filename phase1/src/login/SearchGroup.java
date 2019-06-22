package login;

import java.awt.event.ActionListener;
import java.util.ArrayList;

interface SearchGroup {
    ArrayList<SearchObject> getSearchObjects();
    String getSearchKey1();
    String getSearchKey2();
    String getSearchKey3();
    String getSelectText();
    String getPage();
    ActionListener getSortAction1();
    ActionListener getSortAction2();
    ActionListener getSortAction3();
    ActionListener toPreviousPage();
    ActionListener toNextPage();

}
