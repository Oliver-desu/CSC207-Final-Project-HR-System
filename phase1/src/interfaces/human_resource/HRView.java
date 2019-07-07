package interfaces.human_resource;

import interfaces.SearchKey;
import interfaces.menu.MenuView;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HRView extends MenuView {

    // the interviewer that is going to match.
    SearchKey getMatchingInterviewer() {
        return null;
    }

    // get decided applications.
    ArrayList<SearchKey> getDecidedApplications() {
        return null;
    }

    SearchKey getCurrentJobPosting() {
        return null;
    }

    void addMatchListener(ActionListener listener) {
    }

    void addDecideListener(ActionListener listener) {
    }

    // do different update with different messages.
    void update(String message) {
        // "match an interviewer."
        // "the job decided who to be hired."
    }
}
