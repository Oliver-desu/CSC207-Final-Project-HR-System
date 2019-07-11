package domain;

import views.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExampleFrame extends JFrame {

    TheSystem system = TheSystem.getInstance();
    private User currentUser;

    private UserPanel userPanel = new UserPanel();
    private MainPanel applicantPanel = new ApplicantMainPanel();
    private MainPanel hrPanel = new HRMainPanel();
    private MainPanel interviewerPanel = new InterviewerMainPanel();

    private Panel panelContainer = new Panel();
    private CardLayout cardLayout = new CardLayout();

    public ExampleFrame() {

        panelContainer.setLayout(cardLayout);


        panelContainer.add(userPanel, "userCard");
        panelContainer.add(applicantPanel, "applicantCard");
        panelContainer.add(interviewerPanel, "interviewerCard");
        panelContainer.add(hrPanel, "hrCard");

        panelContainer.setVisible(true);
        cardLayout.show(panelContainer, "userCard");

    }

    public class UserPanel extends JPanel {

        JTextField username, password, companyName, interviewer;
        JLabel nameLable, passwordLable, companyNameLable, userTypeLable, positionLable, interviewerLabel;
        JComboBox position, userType;
        JButton login, register;
        String[] userTypes = {"company", "applicant"};
        String[] positions = {"interviewer", "human resource"};


        UserPanel() {
            //Todo: setup components, add components

            //ActionListener 里panel switch
            login.addActionListener(new LoginListener());
            register.addActionListener(new RegisterListener());
        }


        public class RegisterListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                User user = system.accountManager.registerUser(username.getText(), password.getText(), (String) userType.getSelectedItem());

                if (user == null) {
                    JOptionPane.showMessageDialog(userPanel, "user already exists, please log in");
                } else {
                    currentUser = user;
                    if (user instanceof Applicant) {
                        applicantPanel.setVisible(true);
                        JOptionPane.showMessageDialog(applicantPanel, "You've log in as a new Applicant");
                    } else if (position.getSelectedItem() == "Human Resource Department") {
                        hrPanel.setVisible(true);
                        JOptionPane.showMessageDialog(hrPanel, "You've log in Human Resource Panel.");
                    } else {
                        interviewer.setVisible(true);
                        String m = system.accountManager.matchInterviewer(companyName.getText(), interviewer.getText());
                        JOptionPane.showMessageDialog(interviewerPanel, m);
                    }
                }
            }


        }

        public class LoginListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                User user = system.accountManager.getUser(username.getText(), (String) userType.getSelectedItem());

                if (user == null) {
                    JOptionPane.showMessageDialog(userPanel, "user not found");
                } else if (user.matchPassword(password.getText())) {
                    userPanel.setVisible(false);
                    currentUser = user;

                    if (user instanceof Applicant) {
                        applicantPanel.setVisible(true);
                        String m = "You've log in as an Applicant";
                        JOptionPane.showMessageDialog(applicantPanel, m);
                    } else if (position.getSelectedItem() == "Human Resource Department") {
                        hrPanel.setVisible(true);
                        String m = "You've log in Human Resource Panel.";
                        JOptionPane.showMessageDialog(hrPanel, m);
                    } else {
                        interviewerPanel.setVisible(true);
                        String m = system.accountManager.matchInterviewer(companyName.getText(), interviewer.getText());
                        JOptionPane.showMessageDialog(interviewerPanel, m);
                    }
                } else {
                    JOptionPane.showMessageDialog(userPanel, "wrong password");
                }
            }
        }
    }
}











