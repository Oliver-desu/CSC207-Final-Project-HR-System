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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DocumentManageScenario extends Scenario {

    private DocumentManager applicantDocument;
    private DocumentManager applicationDocument;

    public DocumentManageScenario(UserMenu userMenu, DocumentManager applicantDocument, DocumentManager applicationDocument) {
        super(userMenu, LayoutMode.REGULAR);
        this.applicantDocument = applicantDocument;
        this.applicationDocument = applicationDocument;
        this.applicantDocument.updateAllDocuments();
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        JobPosting jobPosting = test.addJobPosting(company);
        Application application = test.addDraftApplicationForJobPosting(applicant, jobPosting);

        new DocumentManageScenario(new UserMenu(), applicant.getDocumentManager(), application.getDocumentManager()).exampleView();
    }

    protected void initFilter() {
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        updateRightFilterContent();
        rightFilterPanel.addSelectionListener(new ShowInfoListener(rightFilterPanel));

        if (applicationDocument != null) {
            FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
            updateLeftFilterContent();
            leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        } else {
            getInputInfoPanel().addTextField("File name:");
        }
    }

    private void updateRightFilterContent() {
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        rightFilterPanel.setFilterContent(new ArrayList<>(applicantDocument.getAllDocuments()));
    }

    private void updateLeftFilterContent() {
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(new ArrayList<>(applicationDocument.getAllDocuments()));
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
                    updateRightFilterContent();
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    return;
                }

            } else {
                Document document = (Document) getFilterPanel(false).getSelectObject();
                if (document != null && applicationDocument.addDocument(document.getDocumentName(), document)) {
                    updateLeftFilterContent();
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
                Document document = (Document) getFilterPanel(false).getSelectObject();
                if (document != null && applicantDocument.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    updateRightFilterContent();
                    return;
                }
            } else {
                Document document = (Document) getFilterPanel(true).getSelectObject();
                if (document != null && applicationDocument.removeDocument(document)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Change is made successfully!");
                    updateLeftFilterContent();
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Sorry! Cannot delete!");
        }
    }
}
