package gui.major;

import domain.applying.Document;
import gui.panels.FilterPanel;
import gui.panels.OutputInfoPanel;
import main.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Class {@code Scenario} setup the panel where main operation take place and
 * contains useful shared methods that all child classes have
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserMenu
 * @see OutputInfoPanel
 * @since 2019-08-05
 */
public abstract class Scenario extends JPanel {

    // Panel size
    private static final int WIDTH = UserMenu.SCENARIO_SIZE.width;
    private static final int HEIGHT = UserMenu.SCENARIO_SIZE.height;

    // Related dimension of components in certain ratio
    protected final Dimension REGULAR_INPUT_SIZE = getDimensionByRatio(1, 0.3);
    protected final Dimension REGISTER_INPUT_SIZE = getDimensionByRatio(1, 0.8);
    protected final Dimension LIST_SIZE = getDimensionByRatio(0.3, 0.5);
    protected final Dimension BUTTON_PANEL_SIZE = getDimensionByRatio(1, 0.2);
    protected final Dimension OUTPUT_SIZE = getDimensionByRatio(0.4, 0.5);

    /**
     * The user menu that contains this panel
     *
     * @see UserMenu
     * @see #getUserMenu()
     * @see #getMain()
     * @see #showMessage(String)
     * @see #confirmAction()
     */
    private UserMenu userMenu;

    /**
     * The panel deal with output text to users
     *
     * @see OutputInfoPanel
     * @see #initOutputInfoPanel()
     * @see #setOutputText(String)
     * @see #showDocument(Document)
     */
    private OutputInfoPanel outputInfoPanel = new OutputInfoPanel(OUTPUT_SIZE);

    /**
     * The boolean that check whether this panel has initialized
     *
     * @see #init()
     */
    private boolean hasInit;

    protected Scenario(UserMenu userMenu, String title) {
        this.userMenu = userMenu;
        setTitle(title);
    }

    public void init() {
        if (!hasInit) {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setLayout(new FlowLayout());
            initComponents();
            hasInit = true;
        }
        update();
    }

    abstract protected void initComponents();

    abstract protected void update();

    private Dimension getDimensionByRatio(double horizontalRatio, double verticalRatio) {
        return new Dimension((int) (WIDTH * horizontalRatio) - 5, (int) (HEIGHT * verticalRatio) - 5);
    }

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
