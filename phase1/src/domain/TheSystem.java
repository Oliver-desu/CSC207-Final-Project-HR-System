package domain;

import views.ViewFrame;
import views.panels.MainPanel;

public class TheSystem {

    private static TheSystem ourInstance = new TheSystem();
    static DocumentManager documentManager;
    static JobPostingManager jobPostingManager;
    static AccountManager accountManager;



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

        ViewFrame view = new ViewFrame();
        MainPanel mainPanel = view.getMainPanel();





    }



}
