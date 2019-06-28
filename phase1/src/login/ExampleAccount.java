package login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExampleAccount implements ActionListener {
    private JButton a, b;
    private JFrame jFrame;
    public static void main(String[] args) {
        ExampleAccount account = new ExampleAccount();
        account.getUI().setVisible(true);
    }
    public JFrame getUI() {
        JFrame jFrame = new JFrame("My account");
        jFrame.setSize(440, 600);

        jFrame.add(a = Tool.createButton("Change password",160, 40, null));
        jFrame.add(b = Tool.createButton("Back",160, 40, null));
        jFrame.add(Tool.createDescriptionArea("<html>name:<br>id:<br>etc..</html>",400,400));
        jFrame.add(Tool.createButton("Upload Document:", 150, 40, null));
        jFrame.add(Tool.createTextField(230,30));
        jFrame.add(Tool.createButton("Past Applications",160, 40, null));
        jFrame.add(Tool.createButton("Current Applications",160, 40, null));
        this.jFrame = jFrame;
        return jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == a){
            System.out.println(1);
            jFrame.setVisible(false);
            new JFrame("???").setVisible(true);
        }
        else if(e.getSource() == b){
            jFrame.setVisible(false);
            new JFrame("!!!").setVisible(true);
        }
    }
}
