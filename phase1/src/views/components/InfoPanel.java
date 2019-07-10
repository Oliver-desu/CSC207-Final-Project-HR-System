package views.components;

import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;

// 3(3)
public class InfoPanel extends JPanel implements ViewComponent {

    private static final Font FONT = new Font("Times New Roman", Font.PLAIN, 15);
    private static final int GAP = 20;

    private JTextArea infoArea = new JTextArea();
    private JTextArea workspace = new JTextArea();
    private boolean hasWorkspace;

    public InfoPanel(Dimension dimension) {
        setup(dimension, hasWorkspace);
    }

    public void update() {
        infoArea.updateUI();
        workspace.updateUI();
        updateUI();
    }

    public void addWorkspace() {
        hasWorkspace = true;
        setup(getSize(), hasWorkspace);
    }

    private void setup(Dimension dimension, boolean hasWorkspace) {
        // panel setting
        setLayout(new FlowLayout());
        setPreferredSize(dimension);

        // setup components
        removeAll();
        Dimension newDimension;
        if (hasWorkspace) {
            newDimension = new Dimension(dimension.width - GAP, (dimension.height - GAP) / 2);
            textAreaSetup(infoArea, newDimension, false);
            textAreaSetup(workspace, newDimension, true);
        } else {
            newDimension = new Dimension(dimension.width - GAP, dimension.height - GAP);
            textAreaSetup(infoArea, newDimension, false);
        }
    }

    public String getInfo() {
        return infoArea.getText();
    }

    public void setInfo(String info) {
        infoArea.setText(info);
    }

    public void editInfoArea() {
        infoArea.setEditable(true);
    }

    public String getWorkspaceText() {
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
