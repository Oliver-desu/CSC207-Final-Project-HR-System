package gui.major;

import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;
import main.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class Scenario extends JPanel {
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private static final int WIDTH = UserMenu.SCENARIO_SIZE.width;
    private static final int HEIGHT = UserMenu.SCENARIO_SIZE.height;

    protected final Dimension REGULAR_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 3 - VERTICAL_GAP);
    protected final Dimension REGISTER_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT * 3 / 4);

    private UserMenu userMenu;
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();
    private LayoutMode mode;
    private int numInit;

    // likely to dependency injection.
    private FilterPanel leftFilterPanel;
    private FilterPanel rightFilterPanel;
    private InputInfoPanel inputInfoPanel;

    protected Scenario(UserMenu userMenu, LayoutMode mode) {
        this.userMenu = userMenu;
        this.mode = mode;
    }

    public void init() {
        if (numInit != 0) return;

        leftFilterPanel = initLeftFilter();
        rightFilterPanel = initRightFilter();
        updateFilterContent();
        initButton();
        inputInfoPanel = initInput();
        initLayout();
        setup();
        numInit++;
    }

    protected FilterPanel initLeftFilter() {
        return new FilterPanel();
    }

    protected FilterPanel initRightFilter() {
        return new FilterPanel();
    }

    protected void updateFilterContent() {

    }

    protected void initButton() {
    }

    protected InputInfoPanel initInput() {
        return new InputInfoPanel();
    }

    private void setup() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
        if (mode.equals(LayoutMode.REGULAR)) {
            add(leftFilterPanel);
            add(rightFilterPanel);
            add(outputInfoPanel);
        }
        add(inputInfoPanel);
        add(buttonPanel);
    }

    private void initLayout() {
        final Dimension LIST_SIZE = new Dimension(WIDTH / 4 - HORIZONTAL_GAP, HEIGHT / 2 - VERTICAL_GAP);
        final Dimension OUTPUT_SIZE = new Dimension(WIDTH / 2 - HORIZONTAL_GAP, HEIGHT / 2 - VERTICAL_GAP);
//        final Dimension REGULAR_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 3);
//        final Dimension REGISTER_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT * 3 / 4);
        final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 6 - VERTICAL_GAP);

        if (mode == LayoutMode.REGULAR) {
            leftFilterPanel.setup(LIST_SIZE);
            rightFilterPanel.setup(LIST_SIZE);
            outputInfoPanel.setup(OUTPUT_SIZE);
//            inputInfoPanel.setup(REGULAR_INPUT_SIZE, false);
//        } else if (mode == LayoutMode.REGISTER) {
//            inputInfoPanel.setup(REGISTER_INPUT_SIZE, true);
        }
        buttonPanel.setup(BUTTON_PANEL_SIZE);
    }

    protected HashMap<String, String> getInputInfoMap() {
        return inputInfoPanel.getInfoMap();
    }

    protected InputInfoPanel getInputInfoPanel() {
        return inputInfoPanel;
    }

    protected void addButton(String buttonName, ActionListener listener) {
        buttonPanel.addButton(buttonName, listener);
    }

    protected void switchScenario(Scenario scenario) {
        getUserMenu().setScenario(scenario);
    }

    protected UserMenu getUserMenu() {
        return userMenu;
    }

    protected Main getMain() {
        return getUserMenu().getMain();
    }

    protected void setOutputText(String text) {
        outputInfoPanel.setOutputText(text);
    }

    protected void showDocument(String documentContent) {
        outputInfoPanel.showDocument(documentContent);
    }

    void showColor() {
        setBackground(Color.WHITE);
        leftFilterPanel.setBackground(Color.BLACK);
        rightFilterPanel.setBackground(Color.RED);
        outputInfoPanel.setBackground(Color.BLUE);
        inputInfoPanel.setBackground(Color.darkGray);
        buttonPanel.setBackground(Color.GREEN);
    }

    protected void exampleView() {
        init();
        showColor();
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(this);
        frame.setVisible(true);
    }

    public class ShowInfoListener implements ListSelectionListener {
        private FilterPanel filterPanel;

        public ShowInfoListener(FilterPanel filterPanel) {
            this.filterPanel = filterPanel;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (filterPanel.getSelectObject() != null) {
                String info = filterPanel.getSelectObject().toString();
                setOutputText(info);
            }
        }
    }

    protected enum LayoutMode {REGULAR, REGISTER, VIEW_ONLY}
}
