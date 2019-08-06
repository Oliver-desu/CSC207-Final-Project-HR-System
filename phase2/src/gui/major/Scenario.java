package gui.major;

import domain.Enums.UserType;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.job.InterviewRound;
import domain.job.JobPosting;
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

    /**
     * create a new {@code UserMenu}with given usermenu{@code UserMenu} and given title
     *
     * @param userMenu the giben usermenu need to be passed in
     * @param title    the title of thie scenario
     * @see gui.scenarios.applicant.DocumentManageScenario#DocumentManageScenario(UserMenu, DocumentManager)
     * @see gui.scenarios.applicant.ApplicationManageScenario#ApplicationManageScenario(UserMenu)
     * @see gui.major.UserRegister#UserRegister(UserMenu, UserType)
     * @see gui.major.UserRegister#UserRegister(UserMenu)
     * @see gui.scenarios.applicant.JobSearchingScenario#JobSearchingScenario(UserMenu)
     * @see gui.scenarios.applicant.ViewInterviewScenario#ViewInterviewScenario(UserMenu)
     * @see gui.scenarios.hiringManager.JobPostingRegister#JobPostingRegister(UserMenu)
     * @see gui.scenarios.hiringManager.ViewPostingScenario#ViewPostingScenario(UserMenu)
     * @see gui.scenarios.interviewer.OngoingInterviewScenario#OngoingInterviewScenario(UserMenu)
     * @see gui.scenarios.recruiter.ApplicationScenario#ApplicationScenario(UserMenu)
     * @see gui.scenarios.recruiter.InterviewRoundScenario#InterviewRoundScenario(UserMenu, InterviewRound, JobPosting)
     * @see gui.scenarios.recruiter.JobManageScenario#JobManageScenario(UserMenu)
     * @see gui.scenarios.recruiter.MatchInterviewScenario#MatchInterviewScenario(UserMenu, InterviewRound)
     */
    protected Scenario(UserMenu userMenu, String title) {
        this.userMenu = userMenu;
        setTitle(title);
    }

    /**
     * setup this scenario with given width and height , and update the information in it
     *
     * @see UserMenu#setScenario(Scenario)
     */
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

    /**
     * add the outputInfoPanel to this Jframe
     */
    protected void initOutputInfoPanel() {
        add(outputInfoPanel);
    }

    /**
     * switch to the given scenario through getUserMenu()
     */
    protected void switchScenario(Scenario scenario) {
        getUserMenu().setScenario(scenario);
    }


    protected UserMenu getUserMenu() {
        return userMenu;
    }

    protected Main getMain() {
        return getUserMenu().getMain();
    }

    /**
     * set the text in the outputInfoPanel to be the given text
     * @param text the string need to be showed
     */
    protected void setOutputText(String text) {
        outputInfoPanel.setOutputText(text);
    }

    /**
     * set the document need to be displayed in the outputInfoPanel to be the given document
     * @param document the document need to be showed
     */
    protected void showDocument(Document document) {
        outputInfoPanel.showDocument(document);
    }

    /**
     * add a new showInfoListener to the given filterPanel.
     * @param filterPanel the filterPanel need to be added a lnew ShowInfoListener
     */
    protected void addShowInfoListenerFor(FilterPanel filterPanel) {
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

    /**
     * pop up a new Jframe to show the massage
     * @param message the message need to be showed
     */
    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(getUserMenu(), message, "Message Dialog", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * return true if the user press "yes" button
     */
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

    /**
     * Class {@code ShowInfoListener} implements ListSelectionListener, return  the object selected by the user
     * @see Scenario
     * @since 2019-08-06
     */
    private class ShowInfoListener implements ListSelectionListener {
        /**
         * the filterpanel need to be add a listener
         */
        private FilterPanel filterPanel;

        /**
         * create a new ShowInfoListener with given filterpanel
         * @param filterPanel the given filterpanel
         */
        private ShowInfoListener(FilterPanel filterPanel) {
            this.filterPanel = filterPanel;
        }

        /**
         * overrides the method in interface{@code ListSelectionListener}
         * get the object select by the user , then  set the object.tostring() to the outputpanel
         * @param e ListSelectionEvent
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (filterPanel.getSelectObject() != null) {
                String info = filterPanel.getSelectObject().toString();
                setOutputText(info);
            }
        }
    }
}
