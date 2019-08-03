package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    private static final int MAX_BUTTON_NUM = 5;
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
        Dimension buttonSize = new Dimension(dimension.width / MAX_BUTTON_NUM - HORIZONTAL_GAP,
                dimension.height - VERTICAL_GAP * 2);
        setButtonSize(buttonSize);
    }

    public void addButton(String buttonName, ActionListener listener) {
        if (getComponentCount() >= MAX_BUTTON_NUM) return;
        JButton button = new JButton(buttonName);
        button.setPreferredSize(getButtonSize());
        button.addActionListener(listener);
        add(button);
    }
}