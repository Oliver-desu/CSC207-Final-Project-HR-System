//package oldVersion;
//
//import domain.Interview;
//import domain.Interviewer;
//import oldVersion.Menu;
//import oldVersion.Tool;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class InterviewerMenu extends Menu {
//    private Interviewer interviewer;
//
//    InterviewerMenu(Interviewer interviewer) {
//        this.interviewer = interviewer;
//
//    }
//
//    private void createOngoingInterviewPage() {
//
//    }
//
//    private JPanel createSearchLine(Interview interview) {
//        JButton button = Tool.createSearchButton("edit", new EditInterview(interview));
//        JPanel buttons = Tool.createSearchButtonsArea(button);
//        JPanel info = Tool.createInfoLine(interview);
//        return Tool.createSearchLine(buttons, info);
//    }
//
//    class EditInterview implements ActionListener {
//        private Interview interview;
//
//        EditInterview(Interview interview) {
//            this.interview = interview;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//        }
//    }
//
//}
