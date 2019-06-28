package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tool {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("ExampleMenu");
        addSettings(jFrame);
        JPanel jPanelL = new JPanel();
        setDimension(jPanelL, 150,600);
        jPanelL.setBackground(Color.black);
        JPanel jPanelM = new JPanel();
        setDimension(jPanelM, 650,600);
        jPanelM.setBackground(Color.red);
        JPanel jPanelR = new JPanel();
        setDimension(jPanelR, 400,600);
        jPanelR.setBackground(Color.blue);
        jFrame.add(jPanelL);
        jFrame.add(jPanelM);
        jFrame.add(jPanelR);
        jFrame.setVisible(true);

    }
    static void addSettings(JFrame jFrame){
        jFrame.setSize((1300), (650));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// exit code when close window
        jFrame.setLocationRelativeTo(null);// window pop in the middle of screen
        jFrame.setResizable(false); // window unstretchable
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5)); // flow layout
    }

    private static void setDimension(JComponent jComponent, int width, int height){
        jComponent.setPreferredSize(new Dimension(width, height));
    }

    static JLabel createTextLabel(String text, int width, int height){
        JLabel label=new JLabel(text);
        setDimension(label, width, height);
        return label;
    }
    static JLabel createTextLabel(String text){
        return new JLabel(text);
    }

    static JTextField createTextField(int width, int height){
        JTextField textField = new JTextField();
        setDimension(textField, width, height);
        return textField;
    }

    static JPasswordField createPasswordField(int width, int height){
        JPasswordField passwordField = new JPasswordField();
        setDimension(passwordField,width, height);
        return passwordField;
    }

    static JButton createButton(String text, int width, int height, ActionListener actionListener){
        JButton button = new JButton(text);
        setDimension(button, width, height);
        button.addActionListener(actionListener);
        return button;
    }

    static JCheckBox createCheckBox(int size){
        JCheckBox checkBox = new JCheckBox();
        setDimension(checkBox, size, size);
        return checkBox;
    }

    static JScrollPane createDescriptionArea(String text, int width, int height){
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        setDimension(scrollPane, width, height);
        return scrollPane;
    }

    static void addUserInterface(JFrame jFrame, ActionListener account, ActionListener password, ActionListener logout){
        int width = jFrame.getWidth()/4;
        int height = (int)(width*0.4);
        jFrame.add(createButton("Account", width, height, account));
        jFrame.add(createButton("Edit Password", width, height, password));
        jFrame.add(createButton("Logout", width, height, logout));
    }

    static void addSearchInterface(JFrame jFrame, SearchGroup group, boolean isButton){
        getSearchHeading(jFrame, group);
        getSearchBody(jFrame, group, isButton);
        getSearchPage(jFrame, group.getPage(), group.toPreviousPage(), group.toNextPage());
    }

    private static void getSearchHeading(JFrame jFrame, SearchGroup group){
        int width = jFrame.getWidth()/6;
        int height = (int)(width*0.4);
        jFrame.add(createTextLabel("Sort by:",width, height));
        jFrame.add(createButton(group.getSearchKey1(),width,height,group.getSortAction1()));
        jFrame.add(createButton(group.getSearchKey2(),width,height,group.getSortAction2()));
        jFrame.add(createButton(group.getSearchKey3(),width,height,group.getSortAction3()));
    }
    private static void getSearchBody(JFrame jFrame, SearchGroup group, boolean isButton){
        int width = jFrame.getWidth()/6;
        int height = (int)(width*0.4);
        ArrayList<SearchObject> searchObjects = group.getSearchObjects();
        for (SearchObject searchObject: searchObjects){
            if (isButton)jFrame.add(createButton(group.getSelectText(), width, height, searchObject.getSelectAction()));
            else {
                jFrame.add(createCheckBox(height));
                jFrame.add(createTextLabel("",width/3, height));
            }
            jFrame.add(createTextLabel(searchObject.getSearchValue1(),width, height));
            jFrame.add(createTextLabel(searchObject.getSearchValue2(),width, height));
            jFrame.add(createTextLabel(searchObject.getSearchValue3(),width, height));
        }
    }
    private static void getSearchPage(JFrame jFrame, String page, ActionListener previous, ActionListener next){
        int width = jFrame.getWidth()/4;
        int height = (int)(width*0.4);
        jFrame.add(createButton("Previous", width, height, previous));
        jFrame.add(createTextLabel(page));
        jFrame.add(createButton("Next", width, height, next));
    }
}

