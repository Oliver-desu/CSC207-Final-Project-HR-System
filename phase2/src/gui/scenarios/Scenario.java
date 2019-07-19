package gui.scenarios;

import domain.filter.Filter;
import gui.panels.InputInfoPanel;
import gui.view.UserMenu;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class Scenario {

    Scenario(UserMenu menu, LayoutMode mode) {

    }

    protected void updateFilter() {
    }

    protected void makeUnavailable(ScenarioPart part) {
    }

    protected HashMap<String, String> getInputMap() {
        return null;
    }

    protected void setFilterSelectListener(ListSelectionListener listener) {
    }

    protected void addButton(String buttonName, ActionListener listener) {
    }

    protected InputInfoPanel getInputInfoPanel() {
        return null;
    }

    protected void setOutputText(String text) {
    }

    protected void switchScenario(Scenario scenario) {
    }

    protected UserMenu getUserMenu() {
        return null;
    }

    protected void addFilter(boolean left, Filter filter) {
    }

    protected enum LayoutMode {REGULAR, REGISTER}

    protected enum ScenarioPart {LEFT_FILTER, RIGHT_FILTER, INPUT, OUTPUT, BUTTON}
}
