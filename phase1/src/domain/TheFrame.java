/*
 * Created by JFormDesigner on Wed Jul 10 23:49:31 EDT 2019
 */

package domain;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * @author Jin Zhang
 */
public class TheFrame extends JFrame {

    TheSystem system = TheSystem.getInstance();
    private User currentUser;
    String[] userTypes = {"company","applicant"};
    String[] positions = {"interviewer","human resource"};

    public TheFrame() {
        initComponents();
        cardLayout.show(panelContainer, "userCard");
    }




    private void logInActionPerformed(ActionEvent e) {
        User user = system.accountManager.getUser(username.getText(),(String)userType.getSelectedItem());

        if (user == null) {
            JOptionPane.showMessageDialog(userPanel, "user not found");
        } else if (user.matchPassword(password.getText())) {
            userPanel.setVisible(false);
            this.currentUser = user;

            if (user instanceof Applicant) {
                applicantPanel.setVisible(true);
                String m = "You've log in as an Applicant";
                JOptionPane.showMessageDialog(applicantPanel, m);
            } else if (position.getSelectedItem() == "Human Resource Department") {
                hrPanel.setVisible(true);
                String m = "You've log in Human Resource Panel.";
                JOptionPane.showMessageDialog(hrPanel, m);
            } else {
                interviewer.setVisible(true);
                String m = system.accountManager.matchInterviewer(companyName.getText(), interviewer.getText());
                JOptionPane.showMessageDialog(interviewerPanel, m);
            }
        } else {
            JOptionPane.showMessageDialog(userPanel, "wrong password");
        }


    }




    private void registerActionPerformed(ActionEvent e) {

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
                JOptionPane.showMessageDialog(hrPanel,  "You've log in Human Resource Panel.");
            } else {
                interviewer.setVisible(true);
                String m = system.accountManager.matchInterviewer(companyName.getText(), interviewer.getText());
                JOptionPane.showMessageDialog(interviewerPanel, m);
            }
        }
    }

    private void refeisterActionPerformed(ActionEvent e) {
        // TODO add your code here
    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jin Zhang
        theFrame = new JFrame();
        panelContainer = new JPanel();
        userPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        userType = new JComboBox();
        newCompany = new JRadioButton();
        label4 = new JLabel();
        logIn = new JButton();
        register = new JButton();
        companyName = new JTextField();
        password = new JTextField();
        username = new JTextField();
        label5 = new JLabel();
        position = new JComboBox();
        interviewer = new JTextField();
        label6 = new JLabel();
        hrPanel = new JPanel();
        interviewerPanel = new JPanel();
        applicantPanel = new JPanel();

        //======== theFrame ========
        {
            theFrame.setVisible(true);
            Container theFrameContentPane = theFrame.getContentPane();
            theFrameContentPane.setLayout(null);

            //======== panelContainer ========
            {
                panelContainer.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing
                . border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder
                . CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .
                awt .Font .BOLD ,12 ), java. awt. Color. red) ,panelContainer. getBorder( )) )
                ; panelContainer. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
                ) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} )
                ;
                panelContainer.setLayout(new CardLayout());

                //======== userPanel ========
                {
                    userPanel.setLayout(null);

                    //---- label1 ----
                    label1.setText("username");
                    userPanel.add(label1);
                    label1.setBounds(30, 35, 90, 30);

                    //---- label2 ----
                    label2.setText("password");
                    userPanel.add(label2);
                    label2.setBounds(30, 75, 90, 30);

                    //---- label3 ----
                    label3.setText("user type");
                    userPanel.add(label3);
                    label3.setBounds(30, 125, 90, 30);
                    userPanel.add(userType);
                    userType.setBounds(new Rectangle(new Point(140, 120), userType.getPreferredSize()));

                    //---- newCompany ----
                    newCompany.setText("new company");
                    userPanel.add(newCompany);
                    newCompany.setBounds(new Rectangle(new Point(140, 225), newCompany.getPreferredSize()));

                    //---- label4 ----
                    label4.setText("company name");
                    userPanel.add(label4);
                    label4.setBounds(25, 175, 110, 30);

                    //---- logIn ----
                    logIn.setText("log in");
                    logIn.addActionListener(e -> {
			logInActionPerformed(e);
			logInActionPerformed(e);
		});
                    userPanel.add(logIn);
                    logIn.setBounds(new Rectangle(new Point(160, 400), logIn.getPreferredSize()));

                    //---- register ----
                    register.setText("register");
                    register.addActionListener(e -> {
			refeisterActionPerformed(e);
			registerActionPerformed(e);
		});
                    userPanel.add(register);
                    register.setBounds(345, 395, 98, 38);
                    userPanel.add(companyName);
                    companyName.setBounds(145, 180, 500, 30);
                    userPanel.add(password);
                    password.setBounds(140, 75, 500, 30);
                    userPanel.add(username);
                    username.setBounds(140, 35, 500, 30);

                    //---- label5 ----
                    label5.setText("position");
                    userPanel.add(label5);
                    label5.setBounds(35, 275, 110, 30);
                    userPanel.add(position);
                    position.setBounds(new Rectangle(new Point(150, 275), position.getPreferredSize()));
                    userPanel.add(interviewer);
                    interviewer.setBounds(355, 280, 140, 25);

                    //---- label6 ----
                    label6.setText("name");
                    userPanel.add(label6);
                    label6.setBounds(305, 275, 110, 30);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < userPanel.getComponentCount(); i++) {
                            Rectangle bounds = userPanel.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = userPanel.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        userPanel.setMinimumSize(preferredSize);
                        userPanel.setPreferredSize(preferredSize);
                    }
                }
                panelContainer.add(userPanel, "userCard");

                //======== hrPanel ========
                {
                    hrPanel.setVisible(false);
                    hrPanel.setLayout(null);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < hrPanel.getComponentCount(); i++) {
                            Rectangle bounds = hrPanel.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = hrPanel.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        hrPanel.setMinimumSize(preferredSize);
                        hrPanel.setPreferredSize(preferredSize);
                    }
                }
                panelContainer.add(hrPanel, "hrCard");

                //======== interviewerPanel ========
                {
                    interviewerPanel.setVisible(false);
                    interviewerPanel.setLayout(null);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < interviewerPanel.getComponentCount(); i++) {
                            Rectangle bounds = interviewerPanel.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = interviewerPanel.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        interviewerPanel.setMinimumSize(preferredSize);
                        interviewerPanel.setPreferredSize(preferredSize);
                    }
                }
                panelContainer.add(interviewerPanel, "interviewerCard");

                //======== applicantPanel ========
                {
                    applicantPanel.setVisible(false);
                    applicantPanel.setLayout(null);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < applicantPanel.getComponentCount(); i++) {
                            Rectangle bounds = applicantPanel.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = applicantPanel.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        applicantPanel.setMinimumSize(preferredSize);
                        applicantPanel.setPreferredSize(preferredSize);
                    }
                }
                panelContainer.add(applicantPanel, "applicantCard");
            }
            theFrameContentPane.add(panelContainer);
            panelContainer.setBounds(0, 0, 755, 530);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < theFrameContentPane.getComponentCount(); i++) {
                    Rectangle bounds = theFrameContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = theFrameContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                theFrameContentPane.setMinimumSize(preferredSize);
                theFrameContentPane.setPreferredSize(preferredSize);
            }
            theFrame.pack();
            theFrame.setLocationRelativeTo(theFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jin Zhang
    private JFrame theFrame;
    private JPanel panelContainer;
    private JPanel userPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JComboBox userType;
    private JRadioButton newCompany;
    private JLabel label4;
    private JButton logIn;
    private JButton register;
    private JTextField companyName;
    private JTextField password;
    private JTextField username;
    private JLabel label5;
    private JComboBox position;
    private JTextField interviewer;
    private JLabel label6;
    private JPanel hrPanel;
    private JPanel interviewerPanel;
    private JPanel applicantPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private CardLayout cardLayout;
}
