package views.components;

import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;

// Panel 3(1), 3(2), 5
public class ViewList extends JList<String> implements ViewComponent {
    private static final String[] HR_LIST = new String[]{
            "OPEN", "INTERVIEWING", "WAITING_FOR_NEXT_ROUND", "PENDING", "FILLED", "UNFILLED"
    };
    private static final String[] APPLICANT_LIST = new String[]{
            "INCOMPLETE", "PENDING", "FORWARD", "REJECTED", "HIRED"
    };
    private static final String[] INTERVIEWER_LIST = new String[]{
            "UPCOMING", "WAITING_FOR_RESULT"
    };
    private Type type = Type.OTHER;

    public ViewList(Dimension dimension) {
        setPreferredSize(dimension);
    }

    public void update() {
        // show list depend on different type.
        if (type == Type.HR) setListData(HR_LIST);
        else if (type == Type.APPLICANT) setListData(APPLICANT_LIST);
        else if (type == Type.INTERVIEWER) setListData(INTERVIEWER_LIST);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {HR, INTERVIEWER, APPLICANT, OTHER}
}
