package login;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExampleUser implements SearchGroup, WindowObject {
    public static void main(String[] args) {
        ExampleUser user = new ExampleUser();
        user.getUI().setVisible(true);
    }
    public JFrame getUI(){
        JFrame jFrame = InterfaceTool.createInterface("Some Title", 540, 700);
        InterfaceTool.addUserInterface(jFrame,null, null,null);
        InterfaceTool.addSearchInterface(jFrame,this, false);
        return jFrame;
    }

    @Override
    public String getPage() {
        return "Page ?/?";
    }

    @Override
    public ActionListener getSortAction1() {
        return null;
    }

    @Override
    public ActionListener getSortAction2() {
        return null;
    }

    @Override
    public ActionListener getSortAction3() {
        return null;
    }

    @Override
    public ActionListener toNextPage() {
        return null;
    }

    @Override
    public ActionListener toPreviousPage() {
        return null;
    }

    @Override
    public ArrayList<SearchObject> getSearchObjects() {
        ArrayList<SearchObject> searchObjects = new ArrayList<>();
        for (int i = 0; i<10; i++){
            searchObjects.add(new ExampleObject());
        }
        return searchObjects;
    }

    @Override
    public String getSearchKey1() {
        return "Job";
    }

    @Override
    public String getSearchKey2() {
        return "StartDate";
    }

    @Override
    public String getSearchKey3() {
        return "EndDate";
    }

    @Override
    public String getSelectText() {
        return "Apply";
    }
}
