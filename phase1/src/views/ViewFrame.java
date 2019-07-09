package views;

import views.interfaces.ViewComponent;
import views.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ViewFrame extends JFrame implements ViewComponent {
    // size settings
    private static final int VERTICAL_GAP = 20;
    private static final int LEFT_WIDTH = 800;
    private static final int RIGHT_WIDTH = 130;
    private static final int HORIZONTAL_GAP = 50;
    private static final int TOTAL_WIDTH = LEFT_WIDTH + RIGHT_WIDTH + HORIZONTAL_GAP * 3;
    private static final Dimension FRAME_SIZE = new Dimension(TOTAL_WIDTH, 640);

    // sizes need to pass to components
    public static final Dimension LOGIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 160);
    public static final Dimension SEARACH_PANEL_SIZE = new Dimension(LEFT_WIDTH, 100);
    public static final Dimension MAIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 300);
    public static final Dimension MENU_LIST_SIZE = new Dimension(RIGHT_WIDTH, 350);

    private HashMap<Part, ViewComponent> components = new HashMap<>();
    private HashMap<String, JButton> buttons = new HashMap<>();
    private HashMap<Part, JList<String>> lists = new HashMap<>();
    private HashMap<String, JTextField> textFields = new HashMap<>();
    private HashMap<String, JComboBox<String>> boxes = new HashMap<>();

    public ViewFrame() {
    }

    public void addActionListener(String buttonText, ActionListener listener) {
        buttons.get(buttonText).addActionListener(listener);
    }

    public String getSelectedValue(Part part) {
        JList<String> list = lists.get(part);
        return list.getSelectedValue();
    }

    public String getText(String fieldName) {
        return textFields.get(fieldName).getText();
    }

    // update each component.
    public void update() {
        for (ViewComponent component : components.values()) {
            component.update();
        }
    }

    public void setPartVisibility(Part part, boolean visibility) {
        ViewComponent component = components.get(part);
        component.setVisible(visibility);
    }

    public void setListContent(Part part, String[] content) {
        JList<String> list = lists.get(part);
        list.setListData(content);
    }

    public MainPanel getMainPanel() {
        return new MainPanel();
    }

    public enum Part {LOGIN, SEARCH, MAIN, MENU}
}
