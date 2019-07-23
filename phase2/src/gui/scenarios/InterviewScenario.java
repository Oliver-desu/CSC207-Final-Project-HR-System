package gui.scenarios;

import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InterviewScenario extends Scenario {

    private Applicant applicant;

    public InterviewScenario(UserMenu userMenu, Applicant applicant) {
        super(userMenu, LayoutMode.REGULAR);
        this.applicant = applicant;
    }

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "oliver");
        Applicant applicant = new Applicant(hashMap);
        InterviewScenario scenario = new InterviewScenario(new UserMenu(), applicant);
        scenario.exampleView();
    }

    @Override
    public void init() {
        super.init();
//        Todo: wait Applicant class to solve its bug.
//        Object[] contentArray = applicant.getOngoingInterviews().toArray();
        Object[] contentArray = new Object[]{"This is a string but represent an interview", "same as above"};
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(new ArrayList<>(Arrays.asList(contentArray)));
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        leftFilterPanel.updateUI();
    }

    class ShowInfoListener implements ListSelectionListener {
        private FilterPanel<Object> filterPanel;

        ShowInfoListener(FilterPanel<Object> filterPanel) {
            this.filterPanel = filterPanel;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            String info = filterPanel.getSelectObject().toString();
            setOutputText(info);
        }
    }

}
