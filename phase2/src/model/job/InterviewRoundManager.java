package model.job;

import model.enums.ApplicationStatus;
import model.enums.InterviewRoundStatus;
import model.enums.JobPostingStatus;
import model.exceptions.*;
import model.storage.EmploymentCenter;
import model.user.Applicant;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class {@code InterviewRoundManager} contains all the {@code InterviewRound} happened
 * in a {@code JobPosting}, and processes those interview rounds.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see InterviewRound
 * @see JobPosting
 * @since 2019-08-04
 */
public class InterviewRoundManager implements Serializable {

    private static final long serialVersionUID = 153669577076528602L;

    /**
     * The {@code JobPosting} that this manager is served for.
     *
     * @see #getJobPosting()
     */
    private JobPosting jobPosting;

    /**
     * All the {@code InterviewRound} this manager has.
     *
     * @see InterviewRound
     * @see #getCurrentInterviewRound()
     * @see #getInterviewRounds()
     * @see #addInterviewRound(InterviewRound)
     */
    private ArrayList<InterviewRound> interviewRounds;

    /**
     * All the remaining {@code Application} that has not been rejected.
     *
     * @see #getRemainingApplications()
     * @see #getHiredApplications()
     * @see #applicationCancel(Application)
     */
    private ArrayList<Application> remainingApplications;

    /**
     * Create a new interviewRoundManager.
     *
     * @param jobPosting   the job posting that is related to
     * @param applications applications which will be processed in this round
     * @see JobPosting#startProcessing()
     * @see JobPosting#setInterviewRoundManager()
     */
    InterviewRoundManager(JobPosting jobPosting, ArrayList<Application> applications) {
        this.jobPosting = jobPosting;
        this.interviewRounds = new ArrayList<>();
        this.remainingApplications = applications;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public ArrayList<InterviewRound> getInterviewRounds() {
        return interviewRounds;
    }

    public ArrayList<Application> getRemainingApplications() {
        return remainingApplications;
    }


    /**
     * Get current interview round by searching the first {@code InterviewRound} that is not {@code InterviewRoundStatus.EMPTY}.
     *
     * @return current {@code InterviewRound}
     * @see InterviewRoundManager#nextRound(EmploymentCenter)
     * @see InterviewRoundManager#checkStatus()
     * @see InterviewRoundManager#applicationCancel(Application)
     * @see gui.scenarios.recruiter.InterviewRoundScenario#main(String[])
     */
    public InterviewRound getCurrentInterviewRound() {
        InterviewRound currInterviewRound = null;
        for (InterviewRound interviewRound : interviewRounds) {
            if (interviewRound.getStatus().equals(InterviewRoundStatus.EMPTY)) {
                break;
            } else {
                currInterviewRound = interviewRound;
            }
        }
        return currInterviewRound;
    }

    /**
     * Add a new interview round to {@code interviewRounds}.
     *
     * @param interviewRound the new interview round that waits to be added
     * @see gui.scenarios.recruiter.JobManageScenario
     */
    public void addInterviewRound(InterviewRound interviewRound) {
        interviewRounds.add(interviewRound);
    }

    /**
     * Update the {@code remainingApplications} that contains all applications which have not been rejected.
     *
     * @see InterviewRoundManager#checkStatus()
     * @see JobPosting#endJobPosting()
     */
    void updateRemainingApplications() {
        ArrayList<Application> tempApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (!application.getStatus().equals(ApplicationStatus.REJECTED)) {
                tempApplications.add(application);
            }
        }
        remainingApplications = tempApplications;
    }

    /**
     * When the job posting is in the status {@code JobPostingStatus.PROCESSING} and current interview round has a status
     * of {@code InterviewRoundStatus.FINISHED}, we start the next round by calling {@code start} on {@code remainingApplications}.
     * Otherwise, do nothing.
     *
     * @throws WrongJobPostingStatusException     the posting status is wrong
     * @throws WrongInterviewRoundStatusException the interview round is wrong
     * @throws NextRoundDoesNotExistException     the next current does not exits
     * @see gui.scenarios.recruiter.JobManageScenario
     */
    public void nextRound(EmploymentCenter employmentCenter) throws WrongJobPostingStatusException, WrongInterviewRoundStatusException,
            NextRoundDoesNotExistException {
        InterviewRound currentRound = getCurrentInterviewRound();
        if (!jobPosting.getStatus().equals(JobPostingStatus.PROCESSING)) {
            throw new WrongJobPostingStatusException();
        } else if (currentRound != null && !currentRound.getStatus().equals(InterviewRoundStatus.FINISHED)) {
            throw new WrongInterviewRoundStatusException();
        } else if (interviewRounds.size() <= interviewRounds.indexOf(currentRound) + 1) {
            throw new NextRoundDoesNotExistException();
        } else {
            InterviewRound nextRound = interviewRounds.get(interviewRounds.indexOf(currentRound) + 1);
            nextRound.start(remainingApplications);
            notifyAllApplicant(nextRound, remainingApplications, employmentCenter);
        }
    }

    public void notifyAllApplicant(InterviewRound nextround, ArrayList<Application> remainingApplications, EmploymentCenter employmentCenter) {
        for (Application application : remainingApplications
        ) {
            Applicant applicant = application.getApplicant(employmentCenter);
            applicant.addmessage(nextround);

        }
    }

    /**
     * Update the status of current interview round calling {@code checkStatus} on current {@code interviewRound}.
     * Then update {@code remainingApplications} by calling {@code updateRemainingApplications()}.
     *
     * @see gui.scenarios.recruiter.JobManageScenario
     */
    public void checkStatus() {
        InterviewRound interviewRound = getCurrentInterviewRound();
        if (interviewRound != null) interviewRound.checkStatus();
        updateRemainingApplications();
    }

    /**
     * This is a helper method for {@code hire}. It returns all remaining applications which have the status
     * {@code ApplicationStatus.HIRE}.
     *
     * @return {@code ArrayList<Application>} that contains all applications with status {@code ApplicationStatus.HIRE}
     * @see InterviewRoundManager#hire
     */
    private ArrayList<Application> getHiredApplications() {
        ArrayList<Application> hiredApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (application.getStatus().equals(ApplicationStatus.HIRED)) {
                hiredApplications.add(application);
            }
        }
        return hiredApplications;
    }

    /**
     * Check whether current round is finished or not by checking the interview round status.
     * It is a helper method for {@code hire}.
     *
     * @return true if {@code currentRound} is null or {@code InterviewRoundStatus.EMPTY}; false otherwise
     * @see InterviewRoundManager#hire(Application)
     */
    private boolean currentRoundFinished() {
        InterviewRound currentRound = getCurrentInterviewRound();
        return currentRound == null || currentRound.getStatus().equals(InterviewRoundStatus.EMPTY);
    }

    /**
     * Hire an applicant by setting its status to {@code ApplicationStatus.HIRE}.
     * This will happen only when the following conditions are satisfied:
     * job posting is in {@code JobPostingStatus.PROCESSING};
     * application is in {@code ApplicationStatus.PENDING};
     * current round is finished;
     * the job is still vacant.
     *
     * @param application the application to be hired
     * @throws WrongJobPostingStatusException  status of job posting is not {@code PROCESSING}
     * @throws WrongApplicationStatusException status of application is not {@code PENDING}
     * @see gui.scenarios.recruiter.InterviewRoundScenario
     */
    public void hire(Application application) throws WrongJobPostingStatusException, WrongApplicationStatusException,
            CurrentRoundUnfinishedException, JobPostingAlreadyFilledException {
        if (!jobPosting.getStatus().equals(JobPostingStatus.PROCESSING)) {
            throw new WrongJobPostingStatusException();
        } else if (!application.getStatus().equals(ApplicationStatus.PENDING)) {
            throw new WrongApplicationStatusException();
        } else if (!currentRoundFinished()) {
            throw new CurrentRoundUnfinishedException();
        } else if (jobPosting.getNumOfPositions() <= getHiredApplications().size()) {
            throw new JobPostingAlreadyFilledException();
        } else {
            application.setStatus(ApplicationStatus.HIRED);
        }
    }

    /**
     * Cancel the application that is passed in as input.
     *
     * @param application the application to be canceled
     * @see JobPosting#applicationCancel(Application, EmploymentCenter)
     */
    void applicationCancel(Application application) {
        remainingApplications.remove(application);
        InterviewRound interviewRound = getCurrentInterviewRound();
        if (interviewRound != null) interviewRound.applicationCancel(application);
    }

}
