package gui.scenarios;

import domain.applying.Application;
import domain.applying.Document;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.scenarios.oliver.DocumentManageScenario;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationManageScenario  extends Scenario {
    private  Applicant applicant;
    public ApplicationManageScenario(UserMenu userMenu, Applicant applicant) {

        super(userMenu, LayoutMode.REGULAR);
        this.applicant = applicant;
        System.out.println("created ApplicationManageScenario");
    }

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "oliver");
        Applicant applicant = new Applicant(hashMap);
        ApplicationManageScenario applicationManageScenario = new ApplicationManageScenario(new UserMenu(), applicant);
        applicationManageScenario.exampleView();
    }

    public void init(){
        super.init();
        initLeftFilter();
        initNullButtons();
    }

    public void initNullButtons() {
        NullListener nullListener = new NullListener();
        addButton("EditApplication", nullListener);
        addButton("DeleteApplication", nullListener);
        addButton("Apply", nullListener);
        addButton("WithDraw", nullListener);
        addButton("ViewDocument", nullListener);

    }

    public void initLeftFilter() {
        ArrayList<Object> filterContent = new ArrayList<>(applicant.getApplications());
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        filterPanel.setFilterContent(filterContent);
        setListener(filterPanel);
    }

    public void setListener(FilterPanel filterPanel) {
        filterPanel.addSelectionListener(new LeftFilterListener());
    }

    public void initButtonPanel(Application application, Applicant applicant) {
        ButtonListener buttonListener = new ButtonListener(application, applicant);
        addButton("EditApplication", buttonListener);
        addButton("DeleteApplication", buttonListener);
        addButton("Apply", buttonListener);
        addButton("WithDraw", buttonListener);
        addButton("ViewDocument", buttonListener);
    }

    public class NullListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // null listener ,just in order to show buttons
        }
    }

    public class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(true);
            FilterPanel<Object> rightfilterPanel = getFilterPanel(false);
            Application application = (Application) filterPanel.getSelectObject();
            if (application.getStatus() == Application.ApplicationStatus.DRAFT) {
                initButtonPanel(application, applicant);
            }
            // 如果这个application是draft 那我在这里给button添加真正的按钮和listener
            setOutputText(application.toString());
            ArrayList<Object> documentArrayList = new ArrayList<>(application.getDocumentManager().getAllDocuments());
            rightfilterPanel.setFilterContent(documentArrayList);
            rightfilterPanel.addSelectionListener(new rightFilterListener());
            //在这选定文件会直接弹出窗口显示文件

        }
    }

    public class ButtonListener implements ActionListener {
        private Application application;
        private Applicant applicant;

        public ButtonListener(Application application, Applicant applicant) {
            this.application = application;
            this.applicant = applicant;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonName = e.getActionCommand();
            switch (buttonName) {
                case "ViewDocument":
                    System.out.println(buttonName);
                    break;
                case "EditApplication":
                    DocumentManageScenario documentManageScenario = new DocumentManageScenario(getUserMenu(), applicant.getDocumentManager(), application.getDocumentManager());
                    switchScenario(documentManageScenario);
                    System.out.println("EditApplication");
                    break;
                case "Apply":
                    application.apply(getMain().getJobPool(), getMain().getCompanyPool());
                    System.out.println("Apply successfully");
                case "WithDraw":
                    application.cancel(getMain().getJobPool(), getMain().getCompanyPool());
                    System.out.println("Cancel successfully");
            }
        }
    }

    public class rightFilterListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            FilterPanel<Object> rightfilterPanel = getFilterPanel(false);
            Document document = (Document) rightfilterPanel.getSelectObject();
            showDocument(document.toString());
        }
    }


}
