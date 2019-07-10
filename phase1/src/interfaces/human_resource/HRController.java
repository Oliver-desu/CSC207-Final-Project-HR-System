//package interfaces.human_resource;
//
//import domain.HumanResource;
//import interfaces.InterviewHolder;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class HRController {
//
//    private HRView theView;
//    private HumanResource humanResource;
//    private InterviewHolder interviewHolder;
//
//    HRController(HRView theView, HumanResource humanResource, InterviewHolder interviewHolder) {
//        this.theView = theView;
//        this.humanResource = humanResource;
//        this.interviewHolder = interviewHolder;
//        theView.addMatchListener(new MatchListener());
//        theView.addDecideListener(new DecideListener());
//    }
//
//    class MatchListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
////            SearchKey key1 = theView.getMatchingInterviewer();
////            Interviewer interviewer = humanResource.getInterviewer(key1);
////            SearchKey key2 = theView.getSelectedKey();
////            Interview interview = interviewHolder.getInterview(key2);
////            interview.match(interviewer);
////            theView.update("match an interviewer.");
//        }
//    }
//
//    class DecideListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
////            SearchKey key = theView.getCurrentJobPosting();
////            JobPosting jobPosting = humanResource.getJobPosting(key);
////            ArrayList<SearchKey> keys = theView.getDecidedApplications();
////            ArrayList<Application> applications = jobPosting.getApplications(keys);
////            jobPosting.setFinalHiredApplication(applications);
////            theView.update("the job decided who to be hired.");
//        }
//    }
//
//}
