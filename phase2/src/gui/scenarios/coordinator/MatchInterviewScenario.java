package gui.scenarios.coordinator;

import domain.applying.Application;
import domain.applying.Info;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.storage.Company;
import domain.storage.UserPool;
import domain.user.Interviewer;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchInterviewScenario extends Scenario {

    private InterviewRound interviewRound;

    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound) {
        super(userMenu, LayoutMode.REGULAR);
        this.interviewRound = interviewRound;
    }

    protected void initInput() {
        getInputInfoPanel().addTextField("Dead line:");
    }

    protected void initButton() {
        addButton("Match", new MatchListener());
    }

    protected void initFilter() {
        initLeftFilter();
        initRightFilter();
    }

    private void initLeftFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        ArrayList<Object> applications = new ArrayList<>(interviewRound.getUnmatchedApplications());
        filterPanel.setFilterContent(applications);
    }

    private void initRightFilter() {
        UserPool userPool = getMain().getUserPool();
        Company company = getUserMenu().getCompany();
        ArrayList<Interviewer> interviewers = userPool.getInterviewers(company.getInterviewerIds());
        FilterPanel<Object> filterPanel = getFilterPanel(false);
        filterPanel.setFilterContent(new ArrayList<>(interviewers));
    }

    class MatchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, String> infoMap = getInputInfoMap();
            Interviewer interviewer = (Interviewer) getFilterPanel(false).getSelectObject();
            infoMap.put("interviewerId", interviewer.getUsername());

            Application application = (Application) getFilterPanel(true).getSelectObject();
            Interview interview = application.getInterviewByRound(interviewRound.getRoundName());
            Info interviewInfo = new Info(interview, infoMap);
            interview.match(interviewer, interviewInfo);
        }
    }
}
