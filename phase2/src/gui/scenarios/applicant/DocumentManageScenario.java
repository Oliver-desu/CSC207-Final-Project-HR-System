package gui.scenarios.applicant;

import domain.Exceptions.CanNotEditDocumentManagerException;
import domain.Exceptions.DocumentAlreadyExistsException;
import domain.Exceptions.EmptyDocumentNameException;
import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code  DocumentManageScenario } the scenario used for managing document
 *
 * @see gui.major.MenuPanel
 * @see gui.scenarios.applicant
 * @since 2019-08-06
 */
public class DocumentManageScenario extends Scenario {
    /**
     * the {@code DocumentManager} for the user
     */
    private DocumentManager applicantDocumentManager;
    /**
     * the {@code DocumentManager} for a particular application
     */
    private DocumentManager applicationDocumentManager;
    /**
     * the {@code FilterPanel} on the left stored a list of {@code Application} {@code Document}
     */
    private FilterPanel<Document> leftFilter; // contains application document
    /**
     * the {@code FilterPanel} on the middle stored a list of {@code Applicant} {@code Document}
     */
    private FilterPanel<Document> rightFilter; // contains applicant document

    /**
     * constructor for {@code DocumentManageScenario} ,create a new {@code DocumentManageScenario }
     * with given {@code Usermenu} and {@code DocumentManager}
     *
     * @param userMenu            userMenu  need to create this .
     * @param applicationDocument the {@code} DocumentManager passed in.
     */
    public DocumentManageScenario(UserMenu userMenu, DocumentManager applicationDocument) {
        super(userMenu, "Document Manager");
        Applicant applicant = (Applicant) getUserMenu().getUser();
        this.applicantDocumentManager = applicant.getDocumentManager();
        this.applicationDocumentManager = applicationDocument;
        this.applicantDocumentManager.updateAllDocuments();
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        JobPosting jobPosting = test.addJobPosting(company);
        Application application = test.addDraftApplicationForJobPosting(applicant, jobPosting);

        new DocumentManageScenario(new UserMenu(), application.getDocumentManager()).exampleView();
    }

    /**
     * override the method in the parent class
     * initial the components fo this page include LeftFilter,RightFilter,OutputInfoPanel nad BUttonPanel
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "My Documents");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    /**
     * override the method in parent class  {@code Scenario}
     * set the {@code Document} of this applicant to the rightFilter,
     * set the {@code Document} of this applicantion to the lefttFilter.
     */
    @Override
    protected void update() {
        if (applicationDocumentManager != null) {
            leftFilter.setFilterContent(applicationDocumentManager.getAllDocuments());
        }
        rightFilter.setFilterContent(applicantDocumentManager.getAllDocuments());
    }

    /**
     * Initial two button , "Add" button with  new AddDocumentListener,
     * "Delete" button with new DeleteDocumentListener , and them to the {@code ButtonPanel}
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Add", new AddDocumentListener());
        buttonPanel.addButton("Delete", new DeleteDocumentListener());
        add(buttonPanel);
    }


    /**
     * return the path of  file need to be submitted  in the right form.
     * @see gui.scenarios.applicant.DocumentManageScenario.AddDocumentListener#actionPerformed(ActionEvent)
     * @return a string represent the path of the file .
     */
    private String getSubmitFileName() {
        FileDialog fileDialog = new FileDialog(getUserMenu());
        fileDialog.setVisible(true);
        return fileDialog.getDirectory() + "\\" + fileDialog.getFile();
    }

    /**
     * Class {@code  AddDocumentListener}
     * @see #initButton()
     * @since 2019-08-06
     */
    private class AddDocumentListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * If applicationDocumentManager is null then   add
         * the file to this applicantDocumentManager. then update GUI and show a massage "succeed!"
         *  If applicationDocumentManager is not  null,then when user select a document in the rightFilter ,
         *  then add it to this application , show a massage "succeed!"
         *  if user didnot slect a ducument and press "add" , the show a message"No document selected!"
         * @throws CanNotEditDocumentManagerException this document cannot be modified.
         * @exception EmptyDocumentNameException   this document is empty.
         * @exception DocumentAlreadyExistsException   the document with same name already exist
         * @exception NullPointerException
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document;
            DocumentManager manager;
            if (applicationDocumentManager == null) {
                document = new Document(getSubmitFileName());
                manager = applicantDocumentManager;
            } else {
                document = rightFilter.getSelectObject();
                manager = applicationDocumentManager;
            }

            try {
                manager.addDocument(document);
                update();
                showMessage("Succeed!");
            } catch (CanNotEditDocumentManagerException | EmptyDocumentNameException | DocumentAlreadyExistsException e1) {
                showMessage(e1.getMessage());
            } catch (NullPointerException e1) {
                showMessage("No document selected!");
            }
        }
    }

    /**
     * Class {@code  DeleteDocumentListener} listener used for delete document
     * @see #initButton()
     * @since 2019-08-06
     */
    private class DeleteDocumentListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * If applicationDocumentManager is null then   get the file which selected by user and remove it
         * from {@code Applicant} document list,
         * then update GUI and show a massage "Change is made successfully!"
         * If applicationDocumentManager is not null then   get the file which selected by user and remove it
         * from this {@code Application} document list,
         * then update GUI and show a massage "Change is made successfully!"
         * otherwise  show a massage "Sorry! Cannot delete!".
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document;
            DocumentManager manager;
            if (applicationDocumentManager == null) {
                document = rightFilter.getSelectObject();
                manager = applicantDocumentManager;
            } else {
                document = leftFilter.getSelectObject();
                manager = applicationDocumentManager;
            }

            if (document == null) {
                showMessage("No document selected!");
            } else {
                manager.removeDocument(document);
                update();
                showMessage("Succeed!");
            }
        }
    }
}
