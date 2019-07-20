package gui.scenarios;

import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;
import gui.view.UserMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private FilterPanel<Object> leftFilterPanel = new FilterPanel<>();
    private FilterPanel<Object> rightFilterPanel = new FilterPanel<>();
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    Scenario(UserMenu userMenu, LayoutMode mode) {
        setUserMenu(userMenu);
        basicSetup();
        initLayout(mode);
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
        scenario.addButton("123", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object object = scenario.getFilterPanel(true).getSelectObject();
                System.out.println(object);
            }
        });
        ArrayList<Object> strings = new ArrayList<>();
        strings.add("7");
        strings.add("8");
        strings.add("9");
        scenario.getFilterPanel(true).setFilterContent(strings);
        scenario.setOutputText("456");
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

    private void initLayout(LayoutMode mode) {
        leftFilterPanel.setup(LIST_SIZE);
        rightFilterPanel.setup(LIST_SIZE);
        if (mode == LayoutMode.REGULAR) {
            outputInfoPanel.setup(OUTPUT_SIZE);
            inputInfoPanel.setup(REGULAR_INPUT_SIZE, false);
        } else if (mode == LayoutMode.REGISTER) {
            makeUnavailable(outputInfoPanel);
            inputInfoPanel.setup(REGISTER_INPUT_SIZE, true);
        }
        buttonPanel.setup(BUTTON_PANEL_SIZE);
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

    protected FilterPanel<Object> getFilterPanel(boolean left) {
        if (left) return leftFilterPanel;
        else return rightFilterPanel;
    }

    protected enum LayoutMode {REGULAR, REGISTER}

    protected enum ScenarioPart {LEFT_FILTER, RIGHT_FILTER, INPUT, OUTPUT, BUTTON}
}
