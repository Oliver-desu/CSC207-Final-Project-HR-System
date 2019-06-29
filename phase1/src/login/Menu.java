package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(1250, 600);
    private static final Dimension MENU_SIZE = new Dimension(150, 600);
    private static final Dimension PAGE_SIZE = new Dimension(650, 600);
    private static final Dimension INFO_SIZE = new Dimension(400, 600);
    private static final Dimension MENU_BUTTON_SIZE = new Dimension(140, 60);

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
        page1.setPreferredSize(PAGE_SIZE);
        page1.setBackground(Color.BLUE);
        JPanel page2 = new JPanel();
        page2.setPreferredSize(PAGE_SIZE);
        page2.setBackground(Color.BLACK);
        menu.newPage("page1").setBackground(Color.BLACK);
        menu.newPage("page2").setBackground(Color.BLUE);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    Menu(){
        setSize();
    }

    private void setSize(){
        setSize(FRAME_SIZE);
        setLayout(new FlowLayout());
        titlePanel.setPreferredSize(MENU_SIZE);
        titlePanel.setBackground(Color.white);
        pagePanel.setPreferredSize(PAGE_SIZE);
        pagePanel.setBackground(Color.white);
        infoPanel.setPreferredSize(INFO_SIZE);
        infoPanel.setBackground(Color.white);
        add(titlePanel);
        add(pagePanel);
        add(infoPanel);
        titlePanel.setLayout(new FlowLayout());
        pagePanel.setLayout(pages);
        newInfo().add(defaultInfo());
    }

    JPanel newPage(String title){
        JPanel page = new JPanel(new FlowLayout());
        page.setPreferredSize(PAGE_SIZE);
        titlePanel.add(Tool.createButton(title, MENU_BUTTON_SIZE, new SwitchPage(title)));
        pagePanel.add(page, title);
        return page;
    }

    void deletePage(int numPage){
        int num = titlePanel.getComponentCount();
        if(num != numPage) {
            titlePanel.remove(num - 1);
            pagePanel.remove(num - 1);
        }
    }

    JPanel newInfo(){
        deleteInfo();
        JPanel info = new JPanel(new FlowLayout());
        info.setPreferredSize(INFO_SIZE);
        infoPanel.add(info);
        return info;
    }

    void deleteInfo(){
        if (infoPanel.getComponentCount() != 0){
            infoPanel.remove(0);
        }
    }

    JPanel defaultInfo(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(INFO_SIZE);
        return jPanel;
    }

    void showPage(String title){
        pages.show(pagePanel, title);
    }



}
