package gui.scenarios;

import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NullScenario extends Scenario {

    public NullScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        NullScenario scenario = new NullScenario(new UserMenu());
        scenario.init();
        scenario.exampleCall();
    }

    private void exampleCall() {
        showColor();
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(this);
        frame.setVisible(true);
        addButton("123", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputInfoPanel panel = getInputInfoPanel();
                int x = JOptionPane.showConfirmDialog(frame, "Print Yes?", "!!!", JOptionPane.YES_NO_OPTION);
                if (x == 0) System.out.println("Yes");
                else System.out.println("No");
                System.out.println(panel.getInfoMap());
            }
        });
        ArrayList<Object> strings = new ArrayList<>();
        strings.add("7");
        strings.add("8");
        strings.add("9");
        FilterPanel left = getFilterPanel(true);
        left.setFilterContent(strings);
        left.addSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object object = getFilterPanel(true).getSelectObject();
                System.out.println(object);
                JOptionPane.showMessageDialog(frame, "Select Value is Changed");
            }
        });
        setOutputText("456");
        getInputInfoPanel().addTextField("text field:");
        getInputInfoPanel().addComboBox("combo box:", new String[]{"1", "2", "3"});
        getInputInfoPanel().addTextArea("text area:");
        getInputInfoPanel().updateUI();
        showDocument("12345\n12345\n12345\n12345\n12345/n12345/n12345/n12345/n12345/n12345/n");
    }
}
