package gui.scenarios.oliver;

import domain.applying.Document;
import domain.applying.DocumentManager;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    }

    public static void main(String[] args) {
        String x = "aaa\\bbb\\ccc\\dd.txt";
        Path path = Paths.get(x);
        System.out.println(path.toString());
        String name = path.getFileName().toString();
        System.out.println(name);
        String[] y = name.split("[.]");
        System.out.println(y.length);
    }

    @Override
    public void init() {
        super.init();
        initFilter();
        initButton();
    }

    private void initFilter() {
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        rightFilterPanel.setFilterContent(new ArrayList<>(applicantDocument.getAllDocuments()));
        rightFilterPanel.addSelectionListener(new FilterListener(rightFilterPanel));

        if (applicantDocument != null) {
            FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
            leftFilterPanel.setFilterContent(new ArrayList<>(applicationDocument.getAllDocuments()));
            leftFilterPanel.addSelectionListener(new FilterListener(leftFilterPanel));
        } else {
            getInputInfoPanel().addTextField("File name:");
        }
    }

    private void initButton() {
        addButton("Add", new AddDocumentListener());
        addButton("Delete", new DeleteDocumentListener());
    }

    class FilterListener implements ListSelectionListener {

        private FilterPanel filterPanel;

        FilterListener(FilterPanel filterPanel) {
            this.filterPanel = filterPanel;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Document document = (Document) filterPanel.getSelectObject();
            setOutputText(document.toString());
        }
    }

    class AddDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocument == null) {
                Path path = Paths.get(getInputInfoMap().get("File name:"));
                String fileName = path.getFileName().toString().split("[.]")[0];
                applicantDocument.addDocument(fileName, new Document(path));
            } else {
                Document document = (Document) getFilterPanel(false).getSelectObject();
                applicationDocument.addDocument(document.getDocumentName(), document);
            }
        }
    }

    class DeleteDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (applicationDocument == null) {
                Document document = (Document) getFilterPanel(false).getSelectObject();
                applicantDocument.removeDocument(document);
            } else {
                Document document = (Document) getFilterPanel(true).getSelectObject();
                applicationDocument.removeDocument(document);
            }
        }
    }
}