package domain.Enums;

/**
 * Enum {@code InterviewStatus} contains different status of {@code Interview}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see domain.applying.Interview
 * @since 2019-08-04
 */

public enum InterviewStatus {

    /**
     * {@code UNMATCHED} means the {@code Interview} has not been matched by
     * the recruiter.
     */
    UNMATCHED,

    /**
     * {@code PENDING} means the {@code Interview} has been arranged by the
     * recruiter and is waiting for the result from the interviewer.
     */
    PENDING,

    /**
     * {@code PASS} means the {@code Interview} has occurred and the {@code Applicant}
     * has passed it.
     */
    PASS,

    /**
     * {@code REJECTED} means the {@code Interview} has occurred and the {@code Applicant}
     * has been rejected.
     * Note that this means the {@code Application} that contains it has been rejected.
     *
     * @see domain.applying.Application
     * @see ApplicationStatus
     */
    FAIL

}
