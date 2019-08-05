package domain.Enums;

import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.user.Applicant;

/**
 * Class {@code ApplicationStatus} storage the different status of application
 *
 * @see domain.applying.Application#Application(Applicant, JobPosting)
 * @see domain.applying.Application#apply(Storage)
 * @see domain.applying.Application#cancel(Storage)
 * @see domain.applying.Application#update(Interview)
 * @see  InterviewRoundManager#hire(Application)
 * @see JobPosting#endJobPosting()
 * @author group 0120 of CSC207 summer 2019
 */
public enum ApplicationStatus {

    DRAFT,
    PENDING,
    HIRE,
    REJECTED

}
