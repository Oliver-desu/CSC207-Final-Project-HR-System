package gui.scenarios.oliver;

import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class ViewInterviewScenario extends Scenario {

    public ViewInterviewScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    protected void initFilter() {
        Applicant applicant = (Applicant) getUserMenu().getUser();
        ArrayList<Object> contentList = new ArrayList<>(applicant.getOngoingInterviews());
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(contentList);
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
    }
}
