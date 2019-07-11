package controller;

import domain.AccountManager;
import views.ViewFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private ViewFrame view;
    private AccountManager manager;

    public LoginController(ViewFrame view, AccountManager manager) {
        this.view = view;
        this.manager = manager;
        setup();
    }

    private void setup() {
        view.addActionListener("Create Account", new CreateAccount());
    }

    class CreateAccount implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = view.getText("Username");
            String companyName = view.getText("Company Name");
            String password = view.getText("Password");
            String type = view.getBoxSelectedValue("Login Type");
            if (type.equalsIgnoreCase("Company")) {
                manager.registerUser(companyName, password, type);
            } else manager.registerUser(user, password, type);
        }
    }
}
