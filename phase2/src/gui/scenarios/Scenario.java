package gui.scenarios;

import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;
import gui.view.UserMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class Scenario extends JPanel {
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private static final Dimension LIST_SIZE = new Dimension(WIDTH / 5 - HORIZONTAL_GAP, HEIGHT / 2);
    private static final Dimension OUTPUT_SIZE = new Dimension(WIDTH * 3 / 5 - HORIZONTAL_GAP, HEIGHT / 2);
    private static final Dimension REGULAR_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 3);
    private static final Dimension REGISTER_INPUT_SIZE = OUTPUT_SIZE;
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 8);

    private UserMenu userMenu;
    private FilterPanel leftFilterPanel = new FilterPanel();
    private FilterPanel rightFilterPanel = new FilterPanel();
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    Scenario(UserMenu userMenu, LayoutMode mode) {
        setUserMenu(userMenu);
        basicSetup();
        if (mode == LayoutMode.REGULAR) initRegularLayout();
        else if (mode == LayoutMode.REGISTER) initRegisterLayout();
    }

    public static void main(String[] args) {
        Scenario scenario = new Scenario(new UserMenu(), LayoutMode.REGULAR) {
        };
        scenario.showColor();
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(scenario);
        frame.setVisible(true);
    }

    private void basicSetup() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
        add(leftFilterPanel);
        add(rightFilterPanel);
        add(outputInfoPanel);
        add(inputInfoPanel);
        add(buttonPanel);
    }

    private void showColor() {
        setBackground(Color.WHITE);
        leftFilterPanel.setBackground(Color.BLACK);
        rightFilterPanel.setBackground(Color.RED);
        outputInfoPanel.setBackground(Color.BLUE);
        inputInfoPanel.setBackground(Color.darkGray);
        buttonPanel.setBackground(Color.GREEN);
    }

    private void initRegularLayout() {
        leftFilterPanel.setPreferredSize(LIST_SIZE);
        rightFilterPanel.setPreferredSize(LIST_SIZE);
        outputInfoPanel.setPreferredSize(OUTPUT_SIZE);
        inputInfoPanel.setPreferredSize(REGULAR_INPUT_SIZE);
        buttonPanel.setPreferredSize(BUTTON_PANEL_SIZE);
    }

    private void initRegisterLayout() {
        leftFilterPanel.setPreferredSize(LIST_SIZE);
        rightFilterPanel.setPreferredSize(LIST_SIZE);
        makeUnavailable(outputInfoPanel);
        inputInfoPanel.setPreferredSize(REGISTER_INPUT_SIZE);
        buttonPanel.setPreferredSize(BUTTON_PANEL_SIZE);
    }

    private void makeUnavailable(JPanel panel) {
        panel.setVisible(false);
    }

    protected void makeUnavailable(ScenarioPart part) {
        if (part == ScenarioPart.INPUT) makeUnavailable(inputInfoPanel);
        else if (part == ScenarioPart.OUTPUT) makeUnavailable(outputInfoPanel);
        else if (part == ScenarioPart.LEFT_FILTER) makeUnavailable(leftFilterPanel);
        else if (part == ScenarioPart.RIGHT_FILTER) makeUnavailable(rightFilterPanel);
        else if (part == ScenarioPart.BUTTON) makeUnavailable(buttonPanel);
    }

    protected HashMap<String, String> getInputInfoMap() {
        return inputInfoPanel.getInfoMap();
    }

    protected void addButton(String buttonName, ActionListener listener) {
        buttonPanel.addButton(buttonName, listener);
    }

    protected InputInfoPanel getInputInfoPanel() {
        return inputInfoPanel;
    }

    protected void setOutputText(String text) {
        outputInfoPanel.setOutputText(text);
    }

    protected void switchScenario(Scenario scenario) {
        getUserMenu().switchScenario(scenario);
    }

    protected UserMenu getUserMenu() {
        return userMenu;
    }

    private void setUserMenu(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    protected FilterPanel getFilterPanel(boolean left) {
        if (left) return leftFilterPanel;
        else return rightFilterPanel;
    }

    protected enum LayoutMode {REGULAR, REGISTER}

    protected enum ScenarioPart {LEFT_FILTER, RIGHT_FILTER, INPUT, OUTPUT, BUTTON}
}
