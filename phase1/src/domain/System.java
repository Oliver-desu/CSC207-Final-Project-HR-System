package domain;

import views.ViewFrame;

public class System {

    private static System ourInstance = new System();
    private DocumentManager documentManager;
    private JobPostingManager jobPostingManager;
    private AccountManager accountManager;



    public static System getInstance() {
        return ourInstance;
    }

    private System() {
        documentManager = new DocumentManager();
        jobPostingManager = new JobPostingManager();
        accountManager = new AccountManager();
    }

    public static void main(String[] args) {

        System system = System.getInstance();
        system.documentManager.deleteAllInactiveDocuments();
        system.jobPostingManager.closeJobPostings();

        ViewFrame theView = new ViewFrame();

        theView.setVisible(true);

    }



}
