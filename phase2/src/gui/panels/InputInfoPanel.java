package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InputInfoPanel extends JPanel {

    private static final int HEIGHT = 50;

    private Box box;
    private Dimension labelSize;
    private Dimension toolSize;
    private Dimension areaSize;
    private HashMap<String, JComponent> componentMap;

    public void setup(Dimension dimension, boolean vertical) {
        setLayout(new FlowLayout());
        setPreferredSize(dimension);
        if (vertical) {
            box = Box.createVerticalBox();
            setComponentSizes(dimension.width);
        } else {
            box = Box.createHorizontalBox();
            setComponentSizes(dimension.width / 4);
        }
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);
    }

    private void setComponentSizes(int width) {
        labelSize = new Dimension(width / 5, HEIGHT);
        toolSize = new Dimension(width / 2, HEIGHT);
        areaSize = new Dimension(width / 2, HEIGHT * 3);
    }

    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
    }

    public void addComboBox(String name, String[] options) {
    }

    public void addTextField(String name, String defaultValue, boolean editable) {
    }

    public void addTextField(String name) {
    }

    public void addTextArea(String name, String defaultValue, boolean editable) {
    }

    public void addTextArea(String name) {
    }

    private String getText(JComponent component) {
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        } else if (component instanceof JComboBox) {
            Object object = ((JComboBox) component).getSelectedItem();
            if (object instanceof String) return (String) object;
        } else if (component instanceof JTextArea) {
            return ((JTextArea) component).getText();
        }
        return "";
    }

    public HashMap<String, String> getInfoMap() {
        HashMap<String, String> infoMap = new HashMap<>();
        for (String componentName : componentMap.keySet()) {
            JComponent component = componentMap.get(componentName);
            infoMap.put(componentName, getText(component));
        }
        return infoMap;
    }
}
