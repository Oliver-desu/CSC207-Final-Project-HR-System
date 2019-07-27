package gui.scenarios.oliver;

import domain.job.InterviewRound;
import domain.storage.Company;
import gui.major.Scenario;
import gui.major.UserMenu;

public class MatchInterviewScenario extends Scenario {

    private InterviewRound interviewRound;
    private Company company;

    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound, Company company) {
        super(userMenu, LayoutMode.REGISTER);
        this.company = company;
        this.interviewRound = interviewRound;
    }
}
