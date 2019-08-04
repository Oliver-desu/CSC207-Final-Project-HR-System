package gui.panels;

import domain.applying.Document;

import javax.swing.*;
import java.awt.*;

public class OutputInfoPanel extends JPanel {

    static final Font FONT = new Font("Times New Roman", Font.PLAIN, 15);
    private static final Dimension DOCUMENT_FRAME_SIZE = new Dimension(600, 400);
    private JTextArea textArea = new JTextArea();

    public OutputInfoPanel(Dimension dimension) {
        setup(dimension);
    }

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

    public void showDocument(Document document) {
        OutputInfoPanel outputInfo = new OutputInfoPanel(DOCUMENT_FRAME_SIZE);
        outputInfo.setOutputText(document.toString());
        new DocumentFrame(document.getDocumentName(), outputInfo);
    }

    private class DocumentFrame extends JFrame {

        DocumentFrame(String title, JPanel document) {
            setTitle(title);
            setResizable(false);
            setSize(new Dimension(DOCUMENT_FRAME_SIZE.width + 20, DOCUMENT_FRAME_SIZE.height + 45));
            setLayout(new FlowLayout());
            add(document);
            setVisible(true);
        }
    }
}
