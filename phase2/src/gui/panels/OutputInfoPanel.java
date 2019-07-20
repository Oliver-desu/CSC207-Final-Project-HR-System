package gui.panels;

import javax.swing.*;
import java.awt.*;

public class OutputInfoPanel extends JPanel {

    private JTextArea textArea = new JTextArea();

    public void setup(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);

        Font font = new Font("Times New Roman", Font.PLAIN, 15);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
    }

    public void setOutputText(String text) {
        textArea.setText(text);
    }
}
