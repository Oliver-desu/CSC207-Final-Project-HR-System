package views;

import javax.swing.*;
import java.awt.*;

// 3(3)
public class InfoArea extends JPanel {

    private static final Font FONT = new Font("Times New Roman", Font.PLAIN, 20);
    private static final int GAP = 20;

    private JTextArea infoArea = new JTextArea();
    private JTextArea workspace = new JTextArea();
    private boolean hasWorkspace;

    InfoArea(Dimension dimension) {
        setup(dimension, hasWorkspace);
    }

    void update() {
        infoArea.updateUI();
        workspace.updateUI();
        updateUI();
    }

    void addWorkspace() {
        hasWorkspace = true;
        setup(getSize(), hasWorkspace);
    }

    private void setup(Dimension dimension, boolean hasWorkspace) {
        // panel setting
        setLayout(new FlowLayout());
        setPreferredSize(dimension);

        // setup components
        Dimension newDimension;
        if (hasWorkspace) {
            newDimension = new Dimension(dimension.width - GAP, dimension.height / 3);
            textAreaSetup(infoArea, newDimension, false);
            textAreaSetup(workspace, newDimension, true);
        } else {
            newDimension = new Dimension(dimension.width - GAP, dimension.height - GAP);
            textAreaSetup(infoArea, newDimension, false);
        }
    }

    String getInfo() {
        return infoArea.getText();
    }

    void setInfo(String info) {
        infoArea.setText(info);
    }

    String getWorkspaceText() {
        if (hasWorkspace) return workspace.getText();
        else throw new Error("Workspace not exist!");
    }

    private void textAreaSetup(JTextArea textArea, Dimension dimension, boolean editable) {
        // text area settings
        textArea.setEditable(editable);
        textArea.setLineWrap(true);
        textArea.setFont(FONT);

        // add to scroll pane and panel
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);
    }
}
