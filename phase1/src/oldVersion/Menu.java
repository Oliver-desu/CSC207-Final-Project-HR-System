package oldVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(1250, 650);
    private static final Dimension MENU_SIZE = new Dimension(150, 600);
    private static final Dimension PAGE_SIZE = new Dimension(650, 600);
    private static final Dimension INFO_SIZE = new Dimension(400, 600);
    private static final Dimension MENU_BUTTON_SIZE = new Dimension(140, 60);

    private JPanel titlePanel = new JPanel();
    private JPanel pagePanel = new JPanel();
    JPanel infoPanel = new JPanel();
    private CardLayout pages = new CardLayout();
    private int numPage;

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

    Menu() {
    }

    Menu(int numPage) {
        this.numPage = numPage;
        setSize();
    }

    public static void main(String[] args) {
        Menu menu = new Menu(2);
        JPanel page1 = new JPanel();
        page1.setPreferredSize(PAGE_SIZE);
        page1.setBackground(Color.BLUE);
        JPanel page2 = new JPanel();
        page2.setPreferredSize(PAGE_SIZE);
        page2.setBackground(Color.BLACK);
        menu.addPage("page1", page1);
        menu.addPage("page2", page2);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        changeInfo(createDefaultInfo());
    }

    void addPage(String title, JPanel page) {
        page.setPreferredSize(PAGE_SIZE);
        titlePanel.add(Tool.createButton(title, MENU_BUTTON_SIZE, new SwitchPage(title)));
        pagePanel.add(page, title);
    }

    void deletePage() {
        int num = titlePanel.getComponentCount();
        if(num != numPage) {
            titlePanel.remove(num - 1);
            pagePanel.remove(num - 1);
            titlePanel.updateUI();
        }
    }

    void showPage(String title) {
        pages.show(pagePanel, title);
    }

    void changeInfo(JPanel info) {
        if (infoPanel.getComponentCount() != 0){
            infoPanel.remove(0);
        }
        info.setPreferredSize(INFO_SIZE);
        infoPanel.add(info);
        infoPanel.updateUI();
    }

    JPanel createDefaultInfo() {
        return new JPanel();
    }

    class ViewDescription implements ActionListener {
        private String text;
        private boolean delete;

        ViewDescription(String text, boolean delete) {
            this.text = text;
            this.delete = delete;
        }

        ViewDescription(String text) {
            this.text = text;
            this.delete = true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (delete) deletePage();
            JPanel info = new JPanel();
            info.add(Tool.createDescriptionArea(text));
            changeInfo(info);
        }
    }

    class ReturnToInfo implements ActionListener {
        private JPanel info;

        ReturnToInfo(JPanel info) {
            this.info = info;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deletePage();
            changeInfo(info);
        }
    }



}
