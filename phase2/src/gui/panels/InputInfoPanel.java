package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InputInfoPanel extends JPanel {

    private static final int HEIGHT = 45;

    private Container box;
    private Dimension labelSize;
    private Dimension toolSize;
    private Dimension areaSize;
    private HashMap<String, JComponent> componentMap = new HashMap<>();

    public void setup(Dimension dimension, boolean vertical) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(dimension);
        if (vertical) {
            box = Box.createVerticalBox();
            setComponentSizes(dimension.width);
        } else {
            box = new JPanel(new FlowLayout());
            box.setPreferredSize(new Dimension(dimension.width - 20, dimension.height - 20));
            setComponentSizes(dimension.width / 2);
        }
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);
    }

    private void setComponentSizes(int width) {
        labelSize = new Dimension(width / 5, HEIGHT);
        toolSize = new Dimension(width / 2, HEIGHT);
        areaSize = new Dimension(width * 3 / 4, HEIGHT * 2);
    }

    private void createTool(String name, JComponent component) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(name);
        label.setPreferredSize(labelSize);
        panel.add(label);
        if (component instanceof JTextArea) {
            JScrollPane scrollPane = new JScrollPane(component);
            scrollPane.setPreferredSize(areaSize);
            panel.add(scrollPane);
        } else panel.add(component);
        box.add(panel);
        componentMap.put(name, component);
    }

    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setEditable(editable);
        comboBox.setPreferredSize(toolSize);
        createTool(name, comboBox);
    }

    public void addComboBox(String name, String[] options) {
        addComboBox(name, options, null, false);
    }

    public void addTextField(String name, String defaultValue, boolean editable) {
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEditable(editable);
        textField.setPreferredSize(toolSize);
        createTool(name, textField);
    }

    public void addTextField(String name) {
        addTextField(name, "", true);
    }

    public void addTextArea(String name, String defaultValue, boolean editable) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(OutputInfoPanel.FONT);
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        textArea.setText(defaultValue);
        createTool(name, textArea);
    }

    public void addTextArea(String name) {
        addTextArea(name, "", true);
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
