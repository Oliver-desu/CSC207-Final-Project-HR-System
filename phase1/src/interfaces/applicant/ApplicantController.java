package interfaces.applicant;

import domain.Applicant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicantController {
    private ApplicantView theView;
    private Applicant applicant;

    ApplicantController(ApplicantView theView, Applicant applicant) {
        this.theView = theView;
        this.applicant = applicant;
        theView.addApplyListener(new ApplyListener());
        theView.addUploadListener(new UploadListener());
    }

    class ApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class UploadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
