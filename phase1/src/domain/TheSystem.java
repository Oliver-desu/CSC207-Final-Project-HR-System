package domain;

import views.ViewFrame;
import views.panels.MainPanel;

import java.io.Serializable;

public class TheSystem implements Serializable {

    private DocumentManager documentManager = new DocumentManager();
    private JobPostingManager jobPostingManager = new JobPostingManager();
    private AccountManager accountManager = new AccountManager();

    public static void main(String[] args) {
        TheSystem system = new TheSystem();
        system.documentManager.deleteAllInactiveDocuments();
        system.jobPostingManager.closeJobPostings();

        ViewFrame view = new ViewFrame();
        MainPanel mainPanel = view.getMainPanel();





    }



}
