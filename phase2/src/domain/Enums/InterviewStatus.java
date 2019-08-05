package domain.Enums;

import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.user.Employee;

/**
 * Class {@code InterviewStatus} storage the different status of Interview
 * @see domain.applying.Application#update(Interview)
 * @see Interview#match(Employee)
 * @see Interview#setResult(boolean)
 * @see  Interview#cancel()
 * @see  Interview#toString()
 * @see InterviewRound#getUnmatchedApplications()
 * @see  InterviewRound#checkStatus()
 * @author group 0120 of CSC207 summer 2019
 */

public enum InterviewStatus {

    UNMATCHED,
    PENDING,
    PASS,
    FAIL

}
