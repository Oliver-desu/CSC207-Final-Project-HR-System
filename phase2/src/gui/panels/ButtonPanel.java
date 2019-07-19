package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;
    private Dimension buttonSize;

    private Dimension getButtonSize() {
        return buttonSize;
    }

    private void setButtonSize(Dimension buttonSize) {
        this.buttonSize = buttonSize;
    }

    public void setup(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
        Dimension buttonSize = new Dimension(getWidth() / 5 - HORIZONTAL_GAP,
                getHeight() - VERTICAL_GAP);
        setButtonSize(buttonSize);
    }

    public void addButton(String buttonName, ActionListener listener) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(getButtonSize());
        button.addActionListener(listener);
        add(button);
        updateUI();
    }
}