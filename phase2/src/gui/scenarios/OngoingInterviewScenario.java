package gui.scenarios;

import domain.applying.Application;
import domain.applying.Interview;
import domain.user.Interviewer;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class OngoingInterviewScenario extends Scenario {

    private Interviewer interviewer;
    private Interview interview = new Interview(new Application(new HashMap<>()));

    public OngoingInterviewScenario(UserMenu userMenu, Interviewer interviewer){
        super(userMenu, LayoutMode.REGULAR);
        this.interviewer = interviewer;
    }

    public static void main(String[] args) {
        OngoingInterviewScenario scenario = new OngoingInterviewScenario(new UserMenu(), new Interviewer(new HashMap<>(), "NoId"));
        scenario.init();
    }

    public void init() {
        super.init();
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(new ArrayList<>(this.interviewer.getUpcomingInterviews()));
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        leftFilterPanel.updateUI();
        OutputInfoPanel rightOutputPanel = new OutputInfoPanel();
        rightOutputPanel.setOutputText(this.interview.toString());
        InputInfoPanel InputInfo = getInputInfoPanel();
        InputInfo.addTextField("what");
    }

    class ShowInfoListener implements ListSelectionListener{
        private FilterPanel<Object> filterPanel;

        ShowInfoListener(FilterPanel<Object> filterPanel){this.filterPanel = filterPanel;}

        public void valueChanged(ListSelectionEvent e){
            String info = filterPanel.getSelectObject().toString();
            setOutputText(info);
        }
    }
}
