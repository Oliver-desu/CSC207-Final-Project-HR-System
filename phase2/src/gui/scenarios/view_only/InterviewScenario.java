package gui.scenarios.view_only;

import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class InterviewScenario extends Scenario {

    private Applicant applicant;

    public InterviewScenario(UserMenu userMenu, Applicant applicant) {
        super(userMenu, LayoutMode.REGULAR);
        this.applicant = applicant;
    }

    protected void initFilter() {
        ArrayList<Object> contentList = new ArrayList<>(applicant.getOngoingInterviews());
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(contentList);
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
    }
}
