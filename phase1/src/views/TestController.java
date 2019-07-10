package views;

import views.components.ComboBoxPanel;
import views.components.ViewList;
import views.panels.MainPanel;

// run in console
public class TestController {
    public static void main(String[] args) {
        ViewFrame view = new ViewFrame();
        MainPanel mainPanel = view.getMainPanel();
        String[] text = new String[]{"1", "2", "3"};

        // Make Main and Menu invisible.
        view.setPartVisibility(ViewFrame.Part.MAIN, false);
        view.setPartVisibility(ViewFrame.Part.MENU, false);

        // Example to Make Interviewer MainPanel 3(1), 3(2) and 3(4)shows
        view.setPartVisibility(ViewFrame.Part.MAIN, true);
        mainPanel.setAllVisibility(false);
        mainPanel.setPartVisibility(MainPanel.Part.LEFT_LIST, true);
        mainPanel.setPartVisibility(MainPanel.Part.INFO_PANEL, true);
        mainPanel.setPartVisibility(MainPanel.Part.BOX_PANEL, true);

        // Set Interviewer view of 3(4) box Panel
        mainPanel.setBoxView(ComboBoxPanel.View.INTERVIEWER);

        // switch menu to HR
        view.setPartVisibility(ViewFrame.Part.MENU, true);
        view.setMenuType(ViewList.Type.HR);

        // set some content to left list
        mainPanel.setPartVisibility(MainPanel.Part.LEFT_LIST, true);
        mainPanel.setListContent(true, text);

        // set some content to right list
        mainPanel.setPartVisibility(MainPanel.Part.RIGHT_LIST, true);
        mainPanel.setListContent(false, text);

        // set some content to info
        mainPanel.setPartVisibility(MainPanel.Part.INFO_PANEL, true);
        mainPanel.setInfo("???");


        // remember to update
        view.update();
    }
}
