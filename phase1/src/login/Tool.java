package login;

import domain.Applicant;
import domain.Interview;
import domain.JobPosting;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tool {
    private static final Dimension SEARCH_PAGE_SIZE = new Dimension(650, 500);
    private static final Dimension SEARCH_BUTTON_SIZE = new Dimension(70, 30);
    private static final Dimension REGULAR_BUTTON_SIZE = new Dimension(100, 40);
    private static final Dimension REGULAR_DESCRIPTION_AREA_SIZE = new Dimension(400, 400);
    private static final Dimension REGULAR_TEXT_FIELD_SIZE = new Dimension(200, 40);
    private static final Dimension INFO_LINE_SIZE = new Dimension(450, 40);
    private static final Dimension SEARCH_BUTTONS_AREA_SIZE = new Dimension(150, 40);
    private static final Dimension SEARCH_LINE_SIZE = new Dimension(650,40);
    private static final int SEARCH_LABEL_HEIGHT = 30;
    private static final int GAP = 5;



    static void addSettings(JFrame jFrame){
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// exit code when close window
        jFrame.setLocationRelativeTo(null);// window pop in the middle of screen
        jFrame.setResizable(false); // window unstretchable
    }

    private static void setDimension(JComponent jComponent, int width, int height){
        jComponent.setPreferredSize(new Dimension(width, height));
    }

    static JLabel createTextLabel(String text, int width, int height){
        JLabel label=new JLabel(text);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        setDimension(label, width, height);
        label.setBorder(BasicBorders.getButtonBorder());
        return label;
    }
    static JLabel createTextLabel(String text){
        return new JLabel(text);
    }

    static JTextField createTextField(Dimension dimension){
        JTextField textField = new JTextField();
        textField.setPreferredSize(dimension);
        return textField;
    }

    static JTextField createTextField(){
        return createTextField(REGULAR_TEXT_FIELD_SIZE);
    }

    static JPasswordField createPasswordField(int width, int height){
        JPasswordField passwordField = new JPasswordField();
        setDimension(passwordField,width, height);
        return passwordField;
    }

    static JButton createButton(String text, Dimension dimension, ActionListener actionListener){
        JButton button = new JButton(text);
        button.setPreferredSize(dimension);
        button.addActionListener(actionListener);
        return button;
    }

    static JButton createButton(String text, ActionListener actionListener){
        return createButton(text, REGULAR_BUTTON_SIZE, actionListener);
    }

    static JButton createSearchButton(String text, ActionListener actionListener){
        return createButton(text, SEARCH_BUTTON_SIZE, actionListener);
    }

    static JCheckBox createCheckBox(int size){
        JCheckBox checkBox = new JCheckBox();
        setDimension(checkBox, size, size);
        return checkBox;
    }

    static JScrollPane createDescriptionArea(String text){
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(REGULAR_DESCRIPTION_AREA_SIZE);
        return scrollPane;
    }

    static JPanel createInfoLine(ArrayList<String> searchValues){
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setPreferredSize(INFO_LINE_SIZE);
        jPanel.setBackground(Color.BLUE);
        int width = INFO_LINE_SIZE.width/searchValues.size() - GAP;
        for (String value: searchValues){
            jPanel.add(createTextLabel(value, width, SEARCH_LABEL_HEIGHT));
        }
        return jPanel;
    }
    public static JPanel createInfoLine(SearchObject searchObject){
        return createInfoLine(HRMenu.exampleGetSearchValues(searchObject));
    }

//    public static JPanel createInfoLine(SearchObject searchObject){
//        return createInfoLine(searchObject.getSearchValues());
//    }

    static JPanel createSearchButtonsArea(JButton button1){
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setPreferredSize(SEARCH_BUTTONS_AREA_SIZE);
        buttons.add(button1);
        buttons.setBackground(Color.BLUE);
        return buttons;
    }

    static JPanel createSearchButtonsArea(JButton button1, JButton button2){
        JPanel buttons = createSearchButtonsArea(button1);
        buttons.add(button2);
        return buttons;
    }

    static JPanel createSearchLine(JPanel buttons, JPanel info){
        JPanel line = new JPanel(new FlowLayout());
        line.setPreferredSize(SEARCH_LINE_SIZE);
        line.add(buttons);
        line.add(info);
        return line;
    }

    static JScrollPane createSearchPage(ArrayList<JPanel> searchLines){
        JPanel jPanel = new JPanel(new FlowLayout());
        int height = GAP;
        for (JPanel searchLine: searchLines ){
            jPanel.add(searchLine);
            height += SEARCH_LINE_SIZE.height + GAP;
        }
        jPanel.setPreferredSize(new Dimension(SEARCH_PAGE_SIZE.width - 20, height));
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setPreferredSize(SEARCH_PAGE_SIZE);
        return jScrollPane;
    }
}

