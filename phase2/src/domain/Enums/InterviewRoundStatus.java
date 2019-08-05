package domain.Enums;

import domain.job.InterviewRound;
import domain.job.InterviewRoundManager;

/**
 * Class {@code InterviewRoundStatus} storage the different status of InterviewRound
 * @see  domain.job.InterviewRound#InterviewRound(String)
 * @see  InterviewRound#checkStatus()
 * @see  InterviewRoundManager#getCurrentInterviewRound()
 * @see  InterviewRoundManager#nextRound()
 * @author group 0120 of CSC207 summer 2019
 */
public enum InterviewRoundStatus {

    EMPTY,
    MATCHING,
    PENDING,
    FINISHED

}
