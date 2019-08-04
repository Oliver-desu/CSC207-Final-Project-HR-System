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
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentManageScenario extends Scenario {

    private DocumentManager applicantDocumentManager;
    private DocumentManager applicationDocumentManager;
    private FilterPanel<Document> leftFilter; // contains application document
    private FilterPanel<Document> rightFilter; // contains applicant document
    private InputInfoPanel infoPanel;

    public DocumentManageScenario(UserMenu userMenu, DocumentManager applicationDocument) {
        super(userMenu);
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
        initInput();
        initButton();
    }

    protected void initInput() {
        infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        factory.addTextField("File name:");
        add(infoPanel);
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE);
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

    class AddDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocumentManager == null) {
                Path path = Paths.get(infoPanel.getInfoMap().get("File name:"));
                String fileName = path.getFileName().toString().split("[.]")[0];

                if (applicantDocumentManager.addDocument(fileName, new Document(path.toString()))) {
                    update();
                    showMessage("Change is made successfully!");
                    return;
                }

            } else {
                Document document = rightFilter.getSelectObject();
                if (document != null && applicationDocumentManager.addDocument(document.getDocumentName(), document)) {
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
