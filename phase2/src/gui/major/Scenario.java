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

    private static final Dimension OUTPUT_SIZE = new Dimension(WIDTH * 3 / 5 - HORIZONTAL_GAP, HEIGHT / 2);

    private UserMenu userMenu;
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();
    private LayoutMode mode;
    private int numInit;

    // likely to dependency injection.
    private FilterPanel<Object> leftFilterPanel = new FilterPanel<>();
    private FilterPanel<Object> rightFilterPanel = new FilterPanel<>();
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();

    protected Scenario(UserMenu userMenu, LayoutMode mode) {
        this.userMenu = userMenu;
        this.mode = mode;
    }

    public void init() {
        if (numInit != 0) return;
        setup();
        initLayout();
        if (mode.equals(LayoutMode.REGULAR)) {
            initFilter();
        }
        initButton();
        initInput();
        numInit++;
    }

    protected void initFilter() {
    }

    protected void initButton() {
    }

    protected void initInput() {
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
        final Dimension LIST_SIZE = new Dimension(WIDTH / 5 - HORIZONTAL_GAP, HEIGHT / 2);
        final Dimension OUTPUT_SIZE = new Dimension(WIDTH * 3 / 5 - HORIZONTAL_GAP, HEIGHT / 2);
        final Dimension REGULAR_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 3);
        final Dimension REGISTER_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT * 3 / 4);
        final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 8);

        if (mode == LayoutMode.REGULAR) {
            leftFilterPanel.setup(LIST_SIZE);
            rightFilterPanel.setup(LIST_SIZE);
            outputInfoPanel.setup(OUTPUT_SIZE);
            inputInfoPanel.setup(REGULAR_INPUT_SIZE, false);
        } else if (mode == LayoutMode.REGISTER) {
            inputInfoPanel.setup(REGISTER_INPUT_SIZE, true);
        }
        buttonPanel.setup(BUTTON_PANEL_SIZE);
    }

    protected FilterPanel<Object> getFilterPanel(boolean left) {
        if (left) return leftFilterPanel;
        else return rightFilterPanel;
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
        OutputInfoPanel document = new OutputInfoPanel();
        document.setup(OUTPUT_SIZE);
        document.setOutputText(documentContent);
        new DocumentFrame(document);
    }

    class DocumentFrame extends JFrame {

        DocumentFrame(JPanel document) {
            setResizable(false);
            setSize(new Dimension(OUTPUT_SIZE.width + 20, OUTPUT_SIZE.height + 45));
            setLayout(new FlowLayout());
            add(document);
            setVisible(true);
        }
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

    protected class ShowInfoListener implements ListSelectionListener {
        private FilterPanel<Object> filterPanel;

        public ShowInfoListener(FilterPanel<Object> filterPanel) {
            this.filterPanel = filterPanel;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            String info = filterPanel.getSelectObject().toString();
            setOutputText(info);
        }
    }

    protected enum LayoutMode {REGULAR, REGISTER, VIEW_ONLY}
}
