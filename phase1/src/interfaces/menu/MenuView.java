package interfaces.menu;

import interfaces.Page;
import interfaces.SearchKey;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {

    // return the select JList object. in panel 3-left. All objects should be able to view.
    // They can implement Viewable, or create an instance which implement Viewable.

    public SearchKey getSelectedKey() {
        return null;
    }

    public void updateContentPanel(JPanel contentPanel) {
    }

    public void addContentUpdateListener(ListSelectionListener listener) {
    }

    public void updatePage(Page page) {
    }

    public void addPageUpdateListener(String title, ActionListener listener) {
    }
}
