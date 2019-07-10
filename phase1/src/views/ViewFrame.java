package views;

import views.components.ViewList;
import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;
import views.panels.LoginPanel;
import views.panels.MainPanel;
import views.panels.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ViewFrame extends JFrame implements ViewComponent, ComboBoxHolder, TextFieldHolder, ButtonHolder {
    // size settings
    private static final int HORIZONTAL_GAP = 50;
    private static final int VERTICAL_GAP = 20;
    private static final int LEFT_WIDTH = 800;
    private static final int RIGHT_WIDTH = 130;
    private static final int TOTAL_WIDTH = LEFT_WIDTH + RIGHT_WIDTH + HORIZONTAL_GAP * 3;

    private static final Dimension FRAME_SIZE = new Dimension(TOTAL_WIDTH, 640);
    private static final Dimension LOGIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 160);
    private static final Dimension SEARCH_PANEL_SIZE = new Dimension(LEFT_WIDTH, 100);
    private static final Dimension MENU_LIST_SIZE = new Dimension(RIGHT_WIDTH, 350);

    // For easy modifying reason, let MainPanel get access to its size.
    public static final Dimension MAIN_PANEL_SIZE = new Dimension(LEFT_WIDTH, 300);

    private LoginPanel loginPanel = new LoginPanel(LOGIN_PANEL_SIZE);
    private SearchPanel searchPanel = new SearchPanel(SEARCH_PANEL_SIZE);
    private MainPanel mainPanel = new MainPanel();
    private ViewList menuList = new ViewList(MENU_LIST_SIZE);

    public ViewFrame() {
        setup();
        update();
    }

    private void setup() {
        // basic settings
        setTitle("Phase 1 Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, 0));
        setSize(FRAME_SIZE);

        // left side setup
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, VERTICAL_GAP));
        left.setPreferredSize(new Dimension(LEFT_WIDTH, FRAME_SIZE.height));
        left.add(loginPanel);
        left.add(searchPanel);
        left.add(mainPanel);

        // add components
        add(left);
        add(menuList);
    }

    public void addActionListener(String buttonText, ActionListener listener) {
        HashMap<String, JButton> buttons = getButtons();
        buttons.get(buttonText).addActionListener(listener);
    }

    public String getMenuSelectedValue() {
        return menuList.getSelectedValue();
    }

    public String getText(String fieldName) {
        HashMap<String, JTextField> textFields = getTextFields();
        return textFields.get(fieldName).getText();
    }

    // update each component.
    public void update() {
        for (ViewComponent component : getViewComponents().values()) {
            component.update();
        }
    }

    public void setPartVisibility(Part part, boolean visibility) {
        ViewComponent component = getViewComponents().get(part);
        component.setVisible(visibility);
    }

    public void setAllVisibility(boolean visibility) {
        for (ViewComponent component : getViewComponents().values()) {
            component.setVisible(visibility);
        }
    }

    public void setMenuType(ViewList.Type type) {
        menuList.setType(type);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
    }

    public void setBoxContent(String boxName, String[] content) {
        JComboBox<String> box = getBoxes().get(boxName);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(content);
        box.setModel(model);
    }

    public String getBoxSelectedValue(String boxName) {
        JComboBox<String> box = getBoxes().get(boxName);
        return (String) box.getSelectedItem();
    }

    public enum Part {LOGIN, SEARCH, MAIN, MENU}

    private HashMap<Part, ViewComponent> getViewComponents() {
        HashMap<Part, ViewComponent> components = new HashMap<>();
        components.put(Part.LOGIN, loginPanel);
        components.put(Part.SEARCH, searchPanel);
        components.put(Part.MAIN, mainPanel);
        components.put(Part.MENU, menuList);
        return components;
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.putAll(loginPanel.getButtons());
        buttons.putAll(searchPanel.getButtons());
        buttons.putAll(mainPanel.getButtons());
        return buttons;
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.putAll(loginPanel.getBoxes());
        boxes.putAll(mainPanel.getBoxes());
        return boxes;
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        HashMap<String, JTextField> textFields = new HashMap<>();
        textFields.putAll(loginPanel.getTextFields());
        textFields.putAll(searchPanel.getTextFields());
        textFields.putAll(mainPanel.getTextFields());
        return textFields;
    }


}
