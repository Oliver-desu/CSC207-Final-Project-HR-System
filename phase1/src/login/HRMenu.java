package login;

import domain.Applicant;
import domain.Company;
import domain.HumanResource;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
        ArrayList<JPanel> searchLines = new ArrayList<>();
//        for (Applicant applicant: humanResource.getApplicants){
//            searchLines.add(createSearchLine(applicant));
//        }
        JPanel page = new JPanel(new FlowLayout());
//        page.add(Tool.createSearchPage(searchLines));
        addPage("All Applicants", page);
    }

//    JPanel createSearchLine(Applicant applicant){
//        JButton button = Tool.createSearchButton("view", null);
//        JPanel buttons = Tool.createSearchButtonsArea(button);
//        JPanel info = Tool.createInfoLine(applicant);
//        return Tool.createSearchLine(buttons, info);
//    }

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
