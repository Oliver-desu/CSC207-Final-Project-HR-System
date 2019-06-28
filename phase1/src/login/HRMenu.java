package login;

import domain.Applicant;
import domain.Company;
import domain.HumanResource;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;

public class HRMenu extends Menu {
    private HumanResource humanResource;

    public static void main(String[] args) {
        String username = "user";
        String password = "password";
        LocalDate localDate = LocalDate.now();
        Company company = new Company();
        HRMenu hrMenu = new HRMenu(new HumanResource(username, password,localDate, company));
        hrMenu.setVisible(true);
    }

    HRMenu(HumanResource humanResource){
        this.humanResource = humanResource;
        createApplicantPage();
    }

    void createApplicantPage(){
//        JPanel jPanel = new JPanel(new FlowLayout());
//        JScrollPane jScrollPane = new JScrollPane(jPanel);
//        jScrollPane.setPreferredSize(new Dimension(650,500));
//        int height = 5;
//        for (Applicant applicant: humanResource.getApplicants()){
//            JPanel buttons = new JPanel(new FlowLayout());
//            buttons.setPreferredSize(new Dimension(150, 40));
//            buttons.add(Tool.createButton("view", 70,30, null));
//            buttons.add(Tool.createButton("view", 70,30, null));
//            buttons.setBackground(Color.BLUE);
//            jPanel.add(buttons);
//            jPanel.add(Tool.createSearchLine(applicant.getSearchValues()));
//            height += 45;
//        }
//        jPanel.setPreferredSize(new Dimension(630, height));
//
//        JPanel page = new JPanel(new FlowLayout());
//        page.add(jScrollPane);
//        addPage("All Applicants", page);
    }

//    Todo: Copy Paste to Applicant.
//    public ArrayList<String> getSearchValues(){
//        ArrayList<String> list = new ArrayList<>();
//        list.add("value1");
//        list.add("value2");
//        list.add("value3");
//        return list;
//    }

//    Todo: Copy Paste to HumanResource.
//    public ArrayList<Applicant> getApplicants(){
//        ArrayList<Applicant> list = new ArrayList<>();
//        Applicant x = new Applicant("user", "pass", LocalDate.now());
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        list.add(x);
//        return list;
//    }
}
