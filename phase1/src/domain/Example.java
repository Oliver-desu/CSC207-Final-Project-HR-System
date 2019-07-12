package domain;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;



public class Example {


    public static void main(String[] args) throws IOException {
        TheSystem system = TheSystem.getInstance();
        Company company = new Company("Mcdonald", "456");
        ArrayList<String> requirements = new ArrayList<>();
        Applicant applicant = new Applicant("John","123");
        LocalDate closeDate = LocalDate.of(2019,12,3);
        JobPosting jobPosting = new JobPosting("sales",3,closeDate,requirements,company);
        File document;
        Application application = new Application(jobPosting, applicant);
        Interview interview = new Interview("interviewer1",application, LocalDate.now(),company);


        /*
        how to login or register: see code in ExampleFrame
        every time TheSystem runs, DocumentManager will delete find the files in deleteAfterThirtyDays.
        JobPostingManager will close expired jobPostings.
         */
        //----------------------------------After an applicant login----------------------------------------------------------------------------

        //1. how to apply for a jobPosting
        applicant.applyJob(jobPosting);

        //2. how to get the list of incomplete/pending/forward/rejected/hired applications
        ArrayList<Application> applications = applicant.getApplications("incomplete");


        //3. how to show the list of documents attached to an application given the heading of an application
        ArrayList<File> documents = TheSystem.documentManager.getAttachedDocuments(application.getHeading());

        //4. how to get all documents of an applicant
        ArrayList<File> allDocuments = TheSystem.documentManager.getMyDocuments("John");

        //5. how to show the content of a document
        TheSystem.documentManager.viewDocument("file123",application.getHeading());

        //6. how to attach a document to an application
        TheSystem.documentManager.attachDocument("file name",application.getHeading());

        //7. how to add a new document typed by the user and give it a name
        TheSystem.documentManager.createNewDocument("new document 1","",applicant.getUsername());

        //8. how to updated a document
        TheSystem.documentManager.updateDocument("document 1", "123");

        //9. how to drop an application
        applicant.removeApplication(application);

        //10. how to summit a incomplete application
        application.submitApplication();

        //11. how to see the list of interviews of an application (upcoming, pending, rejected, forward)
        application.findInterviews("upcoming");

        //12. how to view the details of an interview --> ToString method in Interview




        //------------------------After the human resource department of a company login------------------------------------------------
        //1. how to get the list of jobPostings of a particular state
             //State: interviewing, waitingForNextRound, Unfilled, Pending, Open, Filled
        company.findJobPostings("interviewing");

        //2. how to get the list of applications of a jobPosting
        jobPosting.getRemainingApplications();

        //3. how to show the detail of a jobPosting--> ToString method in JobPosting

        //4. how to show the list of documents attached to an application
        application.getAttachedDocuments();

        //5. how to show the content of a document(same as Applicant(4))

        //6. how to get the list of interviewers of an company
        company.getInterviewers();

        //7. how to match an application and an interviewer
        //  (i.e create a interview and notify the applicant and the interviewer)
        jobPosting.matchInterview(application,"interviewer1",LocalDate.now());

        //8. how to post a job
        company.postJob(new ArrayList<Object>());

        //9. how to hire a applicant (i.e. change an application's state to hired)
        jobPosting.hire(application);

        //--------------------------After an interviewer of a company login-----------------------------------------------------------------

        //1.how to get the list of upcoming/waitingForResult interviews assigner to an interviewer
        company.findUpcomingInterveiws("interviewer2");
        company.findWaitingForResultInterviews("interviewer2");

        //2. how to show interview details --> ToString method in Interview

        //3. how to give recommendation to a interview (i.e. forward/reject an application)
        interview.giveRecommendation("forward");


    }






}
