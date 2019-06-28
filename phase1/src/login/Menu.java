package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel titlePanel = new JPanel();
    private JPanel pagePanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private CardLayout pages = new CardLayout();

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
        Menu menu = new Menu();
        JPanel page1 = new JPanel();
        page1.setPreferredSize(new Dimension(650, 600));
        page1.setBackground(Color.BLUE);
        JPanel page2 = new JPanel();
        page2.setPreferredSize(new Dimension(650, 600));
        page2.setBackground(Color.BLACK);
        menu.addPage("page1", page1);
        menu.addPage("page2", page2);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    Menu(){
        setSize();
    }

    private void setSize(){
        setSize(1250, 650);
        setLayout(new FlowLayout());
        titlePanel.setPreferredSize(new Dimension(150, 600));
        titlePanel.setBackground(Color.white);
        pagePanel.setPreferredSize(new Dimension(650, 600));
        pagePanel.setBackground(Color.white);
        infoPanel.setPreferredSize(new Dimension(400, 600));
        infoPanel.setBackground(Color.white);
        add(titlePanel);
        add(pagePanel);
        add(infoPanel);
        titlePanel.setLayout(new FlowLayout());
        pagePanel.setLayout(pages);
    }

    void addPage(String title, JPanel page){
        titlePanel.add(Tool.createButton(title, 140, 60, new SwitchPage(title)));
        pagePanel.add(page, title);
    }

    private void showPage(String title){
        pages.show(pagePanel, title);
    }



}
