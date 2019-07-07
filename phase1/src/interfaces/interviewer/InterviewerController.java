package interfaces.interviewer;

import domain.Interviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterviewerController {

    private InterviewerView theView;
    private Interviewer interviewer;

    InterviewerController(InterviewerView theView, Interviewer interviewer) {
        this.theView = theView;
        this.interviewer = interviewer;
        theView.addInterviewPassListener(new InterviewUpdateListener(true));
        theView.addInterviewFailListener(new InterviewUpdateListener(false));
    }

    // when an interview is updated. what should do.
    class InterviewUpdateListener implements ActionListener {
        private boolean result;

        InterviewUpdateListener(boolean result) {
            this.result = result;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            SearchKey key = theView.getSelectedKey();
//            Interview interview = interviewer.getInterviews(key);
//            String recommendation = theView.getRecommendation();
//            interview.setRecommendation(recommendation);
//            if (result) interview.setPass();
//            else interview.setFail();
            theView.update("an interview is finished.");
        }
    }
}
