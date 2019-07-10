package views.panels;

import views.ViewFrame;
import views.components.ButtonPanel;
import views.components.ComboBoxPanel;
import views.components.InfoPanel;
import views.components.ViewList;
import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 3
public class MainPanel extends JPanel implements ViewComponent, ButtonHolder, ComboBoxHolder, TextFieldHolder {

    private static final Dimension PANEL_SIZE = ViewFrame.MAIN_PANEL_SIZE;
    private static final Dimension WINDOW_SIZE = new Dimension(PANEL_SIZE.width / 4, PANEL_SIZE.height / 2);
    private static final Dimension CONTROL_SIZE = new Dimension(PANEL_SIZE.width - 20, PANEL_SIZE.height / 6);
    private ViewList leftList = new ViewList(WINDOW_SIZE);
    private ViewList rightList = new ViewList(WINDOW_SIZE);
    private InfoPanel infoPanel = new InfoPanel(WINDOW_SIZE);
    private ComboBoxPanel boxPanel = new ComboBoxPanel(CONTROL_SIZE);
    private ButtonPanel buttonPanel = new ButtonPanel(CONTROL_SIZE);

    public MainPanel() {
        setup();
    }

    private void setup() {
        // panel settings
        setLayout(new FlowLayout());
        setPreferredSize(PANEL_SIZE);
        leftList.setType(ViewList.Type.OTHER);
        rightList.setType(ViewList.Type.OTHER);

        // add components
        add(leftList);
        add(rightList);
        add(infoPanel);
        add(boxPanel);
        add(buttonPanel);
    }

    @Override
    public void update() {
        leftList.update();
        rightList.update();
        infoPanel.update();
        boxPanel.update();
        buttonPanel.update();
    }

    // choose desired combination of boxes
    public void setBoxView(ComboBoxPanel.View view) {
        boxPanel.setView(view);
    }

    // choose desired combination of buttons
    public void setButtonView(ButtonPanel.View view) {
        buttonPanel.setView(view);
    }

    // split info panel to two halves so that can use second half to write recommendation
    public void addWorkspace() {
        infoPanel.addWorkspace();
    }

    // get the info string from screen
    public String getInfo() {
        return infoPanel.getInfo();
    }

    // set the info string showed to users
    public void setInfo(String info) {
        infoPanel.setInfo(info);
    }

    // you want to edit showed document
    public void editInfoArea() {
        infoPanel.editInfoArea();
    }

    // get the text from workspace, such as finished recommendation
    public String getWorkSpaceText() {
        return infoPanel.getWorkspaceText();
    }

    // you want part of main panel to be shown or hide
    public void setPartVisibility(Part part, boolean visibility) {
        if (part == Part.BOX_PANEL) boxPanel.setVisible(visibility);
        else if (part == Part.BUTTON_PANEL) buttonPanel.setVisible(visibility);
        else if (part == Part.INFO_PANEL) infoPanel.setVisible(visibility);
        else if (part == Part.LEFT_LIST) leftList.setVisible(visibility);
        else if (part == Part.RIGHT_LIST) rightList.setVisible(visibility);
    }

    public void setAllVisibility(boolean visibility) {
        buttonPanel.setVisible(visibility);
        infoPanel.setVisible(visibility);
        boxPanel.setVisible(visibility);
        leftList.setVisible(visibility);
        rightList.setVisible(visibility);
    }

    public void setListContent(boolean left, String[] content) {
        if (left) leftList.setListData(content);
        else rightList.setListData(content);
    }

    public String getListSelectedValue(boolean left) {
        if (left) return leftList.getSelectedValue();
        else return rightList.getSelectedValue();
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.putAll(buttonPanel.getButtons());
        buttons.putAll(boxPanel.getButtons());
        return buttons;
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        return boxPanel.getTextFields();
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        return boxPanel.getBoxes();
    }

    public enum Part {LEFT_LIST, RIGHT_LIST, INFO_PANEL, BOX_PANEL, BUTTON_PANEL}
}
