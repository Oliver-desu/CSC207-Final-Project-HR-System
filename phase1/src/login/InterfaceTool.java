package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfaceTool {
    static JFrame createInterface(String title, int width, int height){
        // create interface with desired setting
        JFrame newInterface = new JFrame(title);
        newInterface.setSize(width,height);
        newInterface.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// exit code when close window
        newInterface.setLocationRelativeTo(null);// window pop in the middle of screen
        newInterface.setResizable(false); // window unstretchable

        // flow layout
        int gapWidth = width/20;
        int gapHeight = gapWidth/4;
        FlowLayout layout=new FlowLayout(FlowLayout.CENTER,gapWidth,gapHeight);
        newInterface.setLayout(layout);
        return newInterface;
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
        JPasswordField jPasswordField = new JPasswordField();
        setDimension(jPasswordField,width, height);
        return jPasswordField;
    }

    static JButton createButton(String text, int width, int height, ActionListener actionListener){
        JButton jButton = new JButton(text);
        setDimension(jButton, width, height);
        jButton.addActionListener(actionListener);
        return jButton;
    }

    static JCheckBox createCheckBox(int size){
        JCheckBox checkBox = new JCheckBox();
        setDimension(checkBox, size, size);
        return checkBox;
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

