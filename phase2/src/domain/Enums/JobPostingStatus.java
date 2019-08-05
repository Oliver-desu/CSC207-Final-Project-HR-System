package domain.Enums;

import domain.applying.Application;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.storage.Storage;

import java.util.HashMap;

/**
 * Class {@code JobPostingStatus} storage the different status of JobPosting
 * @see InterviewRoundManager#nextRound()
 * @see  InterviewRoundManager#hire(Application)
 * @see  domain.job.JobPosting#JobPosting(HashMap)
 * @see JobPosting#startProcessing()
 * @see JobPosting#endJobPosting()
 * @see JobPosting#applicationSubmit(Application, Storage)
 * @see JobPosting#close()
 * @see Storage#getOpenJobPostings()
 * @author group 0120 of CSC207 summer 2019
 */

public enum JobPostingStatus {

    OPEN,
    PROCESSING,
    FINISHED

}
