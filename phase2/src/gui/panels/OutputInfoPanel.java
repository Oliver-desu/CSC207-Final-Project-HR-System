package gui.panels;

import javax.swing.*;
import java.awt.*;

public class OutputInfoPanel extends JPanel {
    static final Font FONT = new Font("Times New Roman", Font.PLAIN, 15);

    private static final Dimension OUTPUT_SIZE = new Dimension(400, 600);
    private JTextArea textArea = new JTextArea();

    public void setup(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);

        textArea.setFont(FONT);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
    }

    public void setOutputText(String text) {
        textArea.setText(text);
    }

    public void showDocument(String documentContent) {
        OutputInfoPanel document = new OutputInfoPanel();
        document.setup(OUTPUT_SIZE);
        document.setOutputText(documentContent);
        new DocumentFrame(document);
    }

    private class DocumentFrame extends JFrame {

        DocumentFrame(JPanel document) {
            setResizable(false);
            setSize(new Dimension(OUTPUT_SIZE.width + 20, OUTPUT_SIZE.height + 45));
            setLayout(new FlowLayout());
            add(document);
            setVisible(true);
        }
    }
}
