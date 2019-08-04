package gui.major;

import domain.applying.Document;
import gui.panels.FilterPanel;
import gui.panels.OutputInfoPanel;
import main.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public abstract class Scenario extends JPanel {
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private static final int WIDTH = UserMenu.SCENARIO_SIZE.width;
    private static final int HEIGHT = UserMenu.SCENARIO_SIZE.height;

    protected final Dimension REGULAR_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT / 3 - VERTICAL_GAP);
    protected final Dimension REGISTER_INPUT_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, HEIGHT * 3 / 4);
    protected final Dimension LIST_SIZE = new Dimension(WIDTH / 4 - HORIZONTAL_GAP, HEIGHT / 2 - VERTICAL_GAP);
    protected final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - HORIZONTAL_GAP, 70);
    protected final Dimension OUTPUT_SIZE = new Dimension(WIDTH / 2 - HORIZONTAL_GAP, HEIGHT / 2 - VERTICAL_GAP);

    private UserMenu userMenu;
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel(OUTPUT_SIZE);
    private boolean hasInit;

    protected Scenario(UserMenu userMenu, String title) {
        this.userMenu = userMenu;
        setTitle(title);
    }

    public void init() {
        if (!hasInit) {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
            initComponents();
            hasInit = true;
        }
        update();
    }

    abstract protected void initComponents();

    abstract protected void update();

    protected void initOutputInfoPanel() {
        add(outputInfoPanel);
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

    protected void showDocument(Document document) {
        outputInfoPanel.showDocument(document);
    }

    protected void addShowInfoListenerFor(FilterPanel filterPanel) {
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(getUserMenu(), message, "Message Dialog", JOptionPane.PLAIN_MESSAGE);
    }

    protected boolean confirmAction() {
        return 0 == JOptionPane.showConfirmDialog(getUserMenu(), "Are you sure?",
                "Confirm Dialog", JOptionPane.YES_NO_OPTION);
    }

    protected void setTitle(String title) {
        getUserMenu().setTitle(title);
    }

    protected void exampleView() {
        init();
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(this);
        frame.setVisible(true);
    }

    private class ShowInfoListener implements ListSelectionListener {
        private FilterPanel filterPanel;

        private ShowInfoListener(FilterPanel filterPanel) {
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
}
