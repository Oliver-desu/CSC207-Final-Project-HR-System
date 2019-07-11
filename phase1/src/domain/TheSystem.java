package domain;

import views.ViewFrame;
import views.panels.MainPanel;

import java.io.Serializable;

public class TheSystem implements Serializable {

    private static TheSystem ourInstance = new TheSystem();

    static DocumentManager documentManager = new DocumentManager();
    static JobPostingManager jobPostingManager = new JobPostingManager();
    static AccountManager accountManager = new AccountManager();

    private TheSystem(){}

    public static TheSystem getInstance() {
        return ourInstance;
    }


    public static void main(String[] args) {
        TheSystem system = new TheSystem();
        system.documentManager.deleteAllInactiveDocuments();
        system.jobPostingManager.closeJobPostings();

        ViewFrame view = new ViewFrame();
        MainPanel mainPanel = view.getMainPanel();





    }



}
