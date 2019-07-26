package gui.scenarios;

import domain.user.Interviewer;
import domain.applying.Interview;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;
import gui.panels.OutputInfoPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class OngoingInterviewScenario extends Scenario {

    private Interviewer interviewer;
    private Interview interview;

    public OngoingInterviewScenario(UserMenu userMenu, Interviewer interviewer){
        super(userMenu, LayoutMode.REGULAR);
        this.interviewer = interviewer;
        this.interview = interview;

    }
    public static void main(String[] args) {
        OngoingInterviewScenario = new OngoingInterviewScenario(new UserMenu());
        scenario.init();

    }

    private void init(){
        super.init();
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(this.interviewer.getUpcomingInterviews());
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        leftFilterPanel.updateUI();
        OutputInfoPanel<Object> rightOutputPanel = new OutputInfoPanel();
        rightOutputPanel.setOutputText(this.interview.toStringForInterviewer());
        InputInfoPanel<Object> InputInfo = getInputInfoPanel();
        InputInfo.addTextField();
        

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
