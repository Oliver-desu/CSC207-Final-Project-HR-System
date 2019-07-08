package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ViewFrame extends JFrame {
    private static final int HORIZONTAL_GAP = 50;
    private static final int VERTICAL_GAP = 20;
    private static final int LEFT_WIDTH = 800;
    private static final int RIGHT_WIDTH = 130;
    private static final int TOTAL_WIDTH = LEFT_WIDTH + RIGHT_WIDTH + HORIZONTAL_GAP * 3;

    private static final Dimension FRAME_SIZE = new Dimension(TOTAL_WIDTH, 640);
    private static final Dimension LOGIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 160);
    private static final Dimension SEARACH_PANEL_SIZE = new Dimension(LEFT_WIDTH, 100);
    private static final Dimension MAIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 250);
    private static final Dimension MENU_LIST_SIZE = new Dimension(RIGHT_WIDTH, 350);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(LEFT_WIDTH, 45);
    private HashMap<Part, ViewComponent> components = new HashMap<>();
    private HashMap<String, JButton> buttons = new HashMap<>();
    private HashMap<Part, JList<String>> lists = new HashMap<>();
    private HashMap<String, JTextField> textFields = new HashMap<>();
    private HashMap<String, JComboBox<String>> boxes = new HashMap<>();

    void addActionListener(String buttonText, ActionListener listener) {
        buttons.get(buttonText).addActionListener(listener);
    }

    String getSelectedValue(Part part) {
        JList<String> list = lists.get(part);
        return list.getSelectedValue();
    }

    String getText(String fieldName) {
        return textFields.get(fieldName).getText();
    }

    // update each component.
    void update() {
        for (ViewComponent component : components.values()) {
            component.update();
        }
    }

    void hidePart(Part part) {
        ViewComponent component = components.get(part);
        component.setVisible(false);
    }

    void showPart(Part part) {
        ViewComponent component = components.get(part);
        component.setVisible(true);
    }

    void setListContent(Part part, String[] content) {
        JList<String> list = lists.get(part);
        list.setListData(content);
    }

    public enum Part {LEFT, RIGHT, LOGIN, SEARCH, MAIN, MENU, BUTTON, LIST_LEFT, LIST_RIGHT, INFO, BOX}
}
