package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentManageScenario extends Scenario {

    private DocumentManager applicantDocument;
    private DocumentManager applicationDocument;
    private FilterPanel<Document> leftFilter; // contains application document
    private FilterPanel<Document> rightFilter; // contains applicant document

    public DocumentManageScenario(UserMenu userMenu, DocumentManager applicationDocument) {
        super(userMenu, LayoutMode.REGULAR);
        Applicant applicant = (Applicant) getUserMenu().getUser();
        this.applicantDocument = applicant.getDocumentManager();
        this.applicationDocument = applicationDocument;
        this.applicantDocument.updateAllDocuments();
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
    protected InputInfoPanel initInput() {
        InputInfoPanel infoPanel = new InputInfoPanel();
        infoPanel.setup(REGULAR_INPUT_SIZE, false);
        infoPanel.addTextField("File name:");
        return infoPanel;
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>();
        leftFilter.addSelectionListener(new ShowInfoListener(leftFilter));
        return leftFilter;
    }

    protected FilterPanel initRightFilter() {
        rightFilter = new FilterPanel<>();
        rightFilter.addSelectionListener(new ShowInfoListener(rightFilter));
        return rightFilter;
    }

    protected void updateFilterContent() {
        if (applicationDocument != null) {
            leftFilter.setFilterContent(applicationDocument.getAllDocuments());
        }
        rightFilter.setFilterContent(applicantDocument.getAllDocuments());
    }

    protected void initButton() {
        addButton("Add", new AddDocumentListener());
        addButton("Delete", new DeleteDocumentListener());
    }

    class AddDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocument == null) {
                Path path = Paths.get(getInputInfoMap().get("File name:"));
                String fileName = path.getFileName().toString().split("[.]")[0];

                if (applicantDocument.addDocument(fileName, new Document(path.toString()))) {
                    updateFilterContent();
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    return;
                }

            } else {
                Document document = rightFilter.getSelectObject();
                if (document != null && applicationDocument.addDocument(document.getDocumentName(), document)) {
                    updateFilterContent();
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Sorry! Cannot Add!");
        }
    }

    class DeleteDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocument == null) {
                Document document = rightFilter.getSelectObject();
                if (document != null && applicantDocument.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    updateFilterContent();
                    return;
                }
            } else {
                Document document = leftFilter.getSelectObject();
                if (document != null && applicationDocument.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    updateFilterContent();
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Sorry! Cannot delete!");
        }
    }
}
