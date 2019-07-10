package views.components;

import views.components.boxes.ApplicantIncompleteBoxPanel;
import views.components.boxes.HRBoxPanel;
import views.components.boxes.InterviewerBoxPanel;
import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ComboBoxPanel extends JPanel implements ViewComponent, TextFieldHolder, ComboBoxHolder, ButtonHolder {

    private View view = View.HR;
    private CardLayout cardLayout = new CardLayout();
    private HashMap<String, JPanel> cards = new HashMap<>();

    public ComboBoxPanel(Dimension dimension) {
        setup(dimension);
    }

    private void setup(Dimension dimension) {
        setLayout(cardLayout);
        setPreferredSize(dimension);

//        addCard(new ApplicantInterviewingBoxPanel(dimension), View.APPLICANT_INTERVIEWING.toString());
        addCard(new ApplicantIncompleteBoxPanel(dimension), View.APPLICANT_INCOMPLETE.toString());
        addCard(new HRBoxPanel(dimension), View.HR.toString());
        addCard(new InterviewerBoxPanel(dimension), View.INTERVIEWER.toString());
    }

    private void addCard(JPanel card, String key) {
        cards.put(key, card);
        add(card, key);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void update() {
        cardLayout.show(this, view.toString());
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        JPanel card = cards.get(view.toString());
        if (card instanceof TextFieldHolder) return ((TextFieldHolder) card).getTextFields();
        throw new Error("No TextFields");
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        JPanel card = cards.get(view.toString());
        if (card instanceof ButtonHolder) return ((ButtonHolder) card).getButtons();
        throw new Error("No Buttons");
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        JPanel card = cards.get(view.toString());
        if (card instanceof ComboBoxHolder) return ((ComboBoxHolder) card).getBoxes();
        throw new Error("No Boxes");
    }

    // abandoned Applicant Interviewing.
    public enum View {HR, INTERVIEWER, /* APPLICANT_INTERVIEWING,*/ APPLICANT_INCOMPLETE}
}
