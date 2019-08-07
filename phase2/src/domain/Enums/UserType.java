package domain.Enums;

import domain.storage.Storage;
import domain.user.Company;
import domain.user.Employee;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code UserType} storage the different types of Users
 * @see Storage#Storage()
 * @see  Storage#getApplicant(String)
 * @see  Storage#getAllApplicants()
 * @see  Storage#getInterviewers(ArrayList)
 * @see  domain.storage.UserFactory#createUser(HashMap, UserType)
 * @see  domain.user.Applicant#Applicant(HashMap)
 * @see  domain.user.Company#Company(HashMap)
 * @see  Company#getHiringManagerId()
 * @see  Company#getRecruiterIds()
 * @see  Company#getInterviewerIds()
 * @see Company#addRecruiterId(String)
 * @see  Company#getInterviewerIds()
 * @see  Employee#getJobPostings()
 * @see  Employee#getInterviews()
 * @author group 0120 of CSC207 summer 2019
 */

public enum UserType {

    APPLICANT,
    INTERVIEWER,
    HIRING_MANAGER,
    RECRUITER

}
