package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicantMenu extends Menu {


    class SwitchPage implements ActionListener {
        private String title;

        SwitchPage(String title){
            this.title = title;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showPage(title);
        }
    }

    public static void main(String[] args) {

    }








}
