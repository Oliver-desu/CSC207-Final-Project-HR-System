package login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowSwitch implements ActionListener {
    private JFrame previous;
    private WindowObject windowObject;
    private JFrame next;

    WindowSwitch(JFrame previous, WindowObject windowObject){
        this.previous = previous;
        this.windowObject = windowObject;
    }

    WindowSwitch(JFrame previous, JFrame next){
        this.previous = previous;
        this.next = next;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        previous.setVisible(false);
        next.setVisible(true);
        windowObject.getUI().setVisible(true);
    }

}
