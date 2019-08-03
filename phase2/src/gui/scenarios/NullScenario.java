package gui.scenarios;

import domain.filter.ExampleFilterable;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NullScenario extends Scenario {

    public NullScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        NullScenario scenario = new NullScenario(new UserMenu());
        scenario.exampleView();
    }

    @Override
    public void init() {
        super.init();
        setupExampleButton();
        setupExampleFilter();
        setupExampleOutput();
        setupExampleInfoPanelLayout();
    }

    private void setupExampleButton() {
        addButton("printer", new ExampleActionListener(this));
    }

    private void setupExampleOutput() {
        setOutputText("set output text here!");
        showDocument("this is a document!");
    }

    private void setupExampleFilter() {
//        ArrayList<Object> strings = new ArrayList<>(getFilterContentExamples());
//        FilterPanel<Object> left = getFilterPanel(true);
//        left.setFilterContent(strings);
//        left.addSelectionListener(new ExampleSelectListener(this));
    }

    private void setupExampleInfoPanelLayout() {
//        getInputInfoPanel().addTextField("text field:");
//        getInputInfoPanel().addComboBox("combo box:", new String[]{"1", "2", "3"});
//        getInputInfoPanel().addTextArea("text area:");
        getInputInfoPanel().updateUI();
    }

    private ArrayList<ExampleFilterable> getFilterContentExamples() {
        ArrayList<ExampleFilterable> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add(new ExampleFilterable(Integer.toString(i)));
        }
        return list;
    }

    class ExampleActionListener implements ActionListener {

        private JComponent parent;


        ExampleActionListener(JComponent parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            InputInfoPanel panel = getInputInfoPanel();
            int x = JOptionPane.showConfirmDialog(parent, "Print Yes?", "!!!", JOptionPane.YES_NO_OPTION);
            if (x == 0) System.out.println("Yes");
            else System.out.println("No");
            System.out.println(panel.getInfoMap());
        }
    }

    class ExampleSelectListener implements ListSelectionListener {

        private JComponent parent;

        ExampleSelectListener(JComponent parent) {
            this.parent = parent;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
//            Object object = getFilterPanel(true).getSelectObject();
//            System.out.println(object);
            JOptionPane.showMessageDialog(parent, "Select Value is Changed");
        }
    }
}
