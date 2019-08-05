package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.lang.Integer.min;

/**
 * Class {@code ButtonPanel} setup gui panel for buttons in horizontal layout
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.Scenario
 * @see gui.major.Login
 * @since 2019-08-05
 */
public class ButtonPanel extends JPanel {

    // Maximum buttons num
    private static final int MAX_BUTTON_NUM = 5;

    // Gaps between buttons and borders
    private static final int HORIZONTAL_GAP = 5;
    private static final int VERTICAL_GAP = 5;

    /**
     * The dimension of buttons
     *
     * @see #getButtonSize()
     * @see #setButtonSize(Dimension)
     * @see #setup(Dimension)
     * @see #addButton(String, ActionListener)
     */
    private Dimension buttonSize;

    public ButtonPanel(Dimension dimension) {
        setup(dimension);
    }

    private Dimension getButtonSize() {
        return buttonSize;
    }

    private void setButtonSize(Dimension buttonSize) {
        this.buttonSize = buttonSize;
    }

    public void setup(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
        int buttonWidth = dimension.width / MAX_BUTTON_NUM - HORIZONTAL_GAP;
        int buttonHeight = min(dimension.height - VERTICAL_GAP * 2, 50);
        setButtonSize(new Dimension(buttonWidth, buttonHeight));
    }

    public void addButton(String buttonName, ActionListener listener) {
        if (getComponentCount() >= MAX_BUTTON_NUM) return;
        JButton button = new JButton(buttonName);
        button.setPreferredSize(getButtonSize());
        button.addActionListener(listener);
        add(button);
    }
}