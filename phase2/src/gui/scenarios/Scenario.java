package gui.scenarios;

import domain.filter.Filter;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;
import gui.view.UserMenu;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class Scenario {

    private UserMenu userMenu;
    private FilterPanel leftFilterPanel = new FilterPanel();
    private FilterPanel rightFilterPanel = new FilterPanel();
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    Scenario(UserMenu userMenu, LayoutMode mode) {
        setUserMenu(userMenu);
        if (mode == LayoutMode.REGULAR) initRegularLayout();
        else if (mode == LayoutMode.REGISTER) initRegisterLayout();
    }

    private void initRegularLayout() {
    }

    private void initRegisterLayout() {
    }

    protected void updateFilter(boolean left) {
        if (left) leftFilterPanel.update();
        else rightFilterPanel.update();
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

    protected HashMap<String, String> getInputMap() {
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

    protected void setFilter(boolean left, Filter filter) {
        if (left) leftFilterPanel.setFilter(filter);
        else rightFilterPanel.setFilter(filter);
    }

    protected enum LayoutMode {REGULAR, REGISTER}

    protected enum ScenarioPart {LEFT_FILTER, RIGHT_FILTER, INPUT, OUTPUT, BUTTON}
}
