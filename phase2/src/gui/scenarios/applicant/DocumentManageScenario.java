package gui.scenarios.applicant;

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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocumentManageScenario extends Scenario {

    private DocumentManager applicantDocumentManager;
    private DocumentManager applicationDocumentManager;
    private FilterPanel<Document> leftFilter; // contains application document
    private FilterPanel<Document> rightFilter; // contains applicant document

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

    @Override
    protected void update() {
        if (applicationDocumentManager != null) {
            leftFilter.setFilterContent(applicationDocumentManager.getAllDocuments());
        }
        rightFilter.setFilterContent(applicantDocumentManager.getAllDocuments());
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Add", new AddDocumentListener());
        buttonPanel.addButton("Delete", new DeleteDocumentListener());
        add(buttonPanel);
    }

    private String getSubmitFileName() {
        FileDialog fileDialog = new FileDialog(getUserMenu());
        fileDialog.setVisible(true);
        return fileDialog.getDirectory() + "\\" + fileDialog.getFile();
    }

    class AddDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocumentManager == null) {
                if (applicantDocumentManager.addDocument(new Document(getSubmitFileName()))) {
                    update();
                    showMessage("Change is made successfully!");
                    return;
                }

            } else {
                Document document = rightFilter.getSelectObject();
                if (document != null && applicationDocumentManager.addDocument(document)) {
                    update();
                    showMessage("Change is made successfully!");
                    return;
                }
            }
            showMessage("Sorry! Cannot Add!");
        }
    }

    class DeleteDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocumentManager == null) {
                Document document = rightFilter.getSelectObject();
                if (document != null && applicantDocumentManager.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    update();
                    return;
                }
            } else {
                Document document = leftFilter.getSelectObject();
                if (document != null && applicationDocumentManager.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    update();
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Sorry! Cannot delete!");
        }
    }
}
