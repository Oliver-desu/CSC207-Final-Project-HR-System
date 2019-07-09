package views.panels;

import views.interfaces.ButtonHolder;
import views.interfaces.TextFieldHolder;

import javax.swing.*;
import java.util.HashMap;

// 1
public class LoginPanel extends JPanel implements ButtonHolder, TextFieldHolder {

    @Override
    public HashMap<String, JButton> getButtons() {
        return null;
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        return null;
    }
}
