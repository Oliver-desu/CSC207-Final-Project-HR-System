package domain;

import views.ViewFrame;

public class TheSystem {

    private static TheSystem ourInstance = new TheSystem();
    private DocumentManager documentManager;
    private JobPostingManager jobPostingManager;
    private AccountManager accountManager;



    public static TheSystem getInstance() {
        return ourInstance;
    }

    private TheSystem() {
        documentManager = new DocumentManager();
        jobPostingManager = new JobPostingManager();
        accountManager = new AccountManager();
    }

    public static void main(String[] args) {

        TheSystem system = TheSystem.getInstance();
        system.documentManager.deleteAllInactiveDocuments();
        system.jobPostingManager.closeJobPostings();



    }



}
