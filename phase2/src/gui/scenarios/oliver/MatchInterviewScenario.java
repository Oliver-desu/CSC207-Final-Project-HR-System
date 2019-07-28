package gui.scenarios.oliver;

import domain.applying.Application;
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
    private Company company;

    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound, Company company) {
        super(userMenu, LayoutMode.REGISTER);
        this.company = company;
        this.interviewRound = interviewRound;
    }

    @Override
    public void init() {
        super.init();
        initLeftFilter();
        initRightFilter();
        initButton();
        initInput();
    }

    // Todo: Not decide what is required to generate a interview.
    private void initInput() {

    }

    private void initButton() {
        addButton("Match", new MatchListener());
    }

    private void initLeftFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        ArrayList<Object> applications = new ArrayList<>(interviewRound.getUnmatchedApplications());
        filterPanel.setFilterContent(applications);
    }

    private void initRightFilter() {
        UserPool userPool = getMain().getUserPool();
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
//            InterviewInfo interviewInfo = new InterviewInfo(infoMap);

            Application application = (Application) getFilterPanel(true).getSelectObject();
            Interview interview = application.getInterviewByRound(interviewRound.getRoundName());
//            interview.match(interviewInfo);
        }
    }
}
