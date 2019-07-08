package views;

import javax.swing.*;
import java.awt.*;

// Panel 3(1), 3(2), 5
public class ViewList extends JList<String> {
    private static final String[] HR_LIST = new String[]{
            "OPEN", "INTERVIEWING", "WAITING_FOR_NEXT_ROUND", "PENDING", "FILLED", "UNFILLED"
    };
    private static final String[] APPLICANT_LIST = new String[]{
            "INCOMPLETE", "PENDING", "INTERVIEWING", "REJECTED", "HIRED"
    };
    private static final String[] INTERVIEWER_LIST = new String[]{
            "UPCOMING", "WAITING_FOR_RESULT", "PAST"
    };
    private Type type;

    ViewList(String type, Dimension dimension) {
        this.type = Type.valueOf(type);
        setPreferredSize(dimension);
    }

    void update() {
        // show list depend on different type.
        if (type == Type.HR) setListData(HR_LIST);
        else if (type == Type.APPLICANT) setListData(APPLICANT_LIST);
        else if (type == Type.INTERVIEWER) setListData(INTERVIEWER_LIST);
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    private enum Type {HR, INTERVIEWER, APPLICANT, OTHER}
}