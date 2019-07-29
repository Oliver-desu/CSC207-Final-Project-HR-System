package gui.scenarios.coordinator;

import domain.applying.Application;
import domain.applying.Document;
import domain.storage.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class ApplicationScenario extends Scenario {

    public ApplicationScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    @Override
    protected void initFilter() {
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        setLeftFilterContent(leftFilterPanel);
        setRightFilterContent(leftFilterPanel, rightFilterPanel);
        leftFilterPanel.addSelectionListener(new LeftFilterListener());
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        rightFilterPanel.addSelectionListener(new ShowInfoListener(rightFilterPanel));
    }

    private void setLeftFilterContent(FilterPanel<Object> leftFilterPanel) {
        Company company = getUserMenu().getCompany();
        leftFilterPanel.setFilterContent(new ArrayList<>(company.getAllApplications()));
    }

    private void setRightFilterContent(FilterPanel<Object> leftFilterPanel, FilterPanel<Object> rightFilterPanel) {
        Application application = (Application) leftFilterPanel.getSelectObject();
        ArrayList<Document> documents = application.getDocumentManager().getAllDocuments();
        rightFilterPanel.setFilterContent(new ArrayList<>(documents));
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            setRightFilterContent(getFilterPanel(true), getFilterPanel(false));
        }
    }


}
