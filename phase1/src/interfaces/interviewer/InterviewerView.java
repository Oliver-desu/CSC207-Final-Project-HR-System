package interfaces.interviewer;

import interfaces.menu.MenuView;

import java.awt.event.ActionListener;

public class InterviewerView extends MenuView {

    String getRecommendation() {
        return null;
    }

    void addInterviewPassListener(ActionListener listener) {
    }

    void addInterviewFailListener(ActionListener listener) {
    }

    // do different update with different messages.
    void update(String message) {
        // "an interview is finished."
    }
}
