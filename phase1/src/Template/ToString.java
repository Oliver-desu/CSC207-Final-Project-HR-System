package Template;

import domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ToString {

    private static List<String> COMPANY = Arrays.asList("Company name", "Company id", "Number of interviewers",
            "Number of current job postings", "Number of all job postings", "Number of applicants currently applied");
    private static List<String> USER = Arrays.asList("User name", "Password", "Date created");
    private static List<String> APPLICANT = Arrays.asList("Real name", "Applicant id", "Number of documents",
            "Number of upcoming interviews", "Number of current applications", "Number of all applications");
    private static List<String> HUMANRESOURCE = Arrays.asList("Company name", "HumanResource id");
    private static List<String> INTERVIEWER = Arrays.asList("Interviewer id", "Real name", "Company name",
            "Number of upcoming interviews");
    private static List<String> JOBPOSTING = Arrays.asList("JobPosting id", "Date posted", "Date closed",
            "Current status", "Number of positions", "Rounds of interviews", "Company name", "Requirements",
            "Number of all applicants", "Number of remaining applicants", "Current round");
    private static List<String> APPLICATION = Arrays.asList("Applicant real name", "JobPosting id", "Documents",
            "Application id");
    private static List<String> INTERVIEW = Arrays.asList("Interview id", "Date", "Location", "Duration",
            "Applicant real name", "Interviewer real name", "JobPosting id", "Application id", "Round", "Status",
            "Recommendation");
    private static List<String> DOCUMENT = Arrays.asList("Document id", "Applicant id", "Applicant real name",
            "Document name", "Content");


    public static List<String> getCOMPANY() {
        return COMPANY;
    }

    public static List<String> getUSER() {
        return USER;
    }

    public static List<String> getAPPLICANT() {
        List<String> applicant= new ArrayList<>();
        applicant.addAll(USER);
        applicant.addAll(APPLICANT);
        return applicant;
    }

    public static List<String> getHUMANRESOURCE() {
        List<String> hr= new ArrayList<>();
        hr.addAll(USER);
        hr.addAll(HUMANRESOURCE);
        return hr;
    }

    public static List<String> getINTERVIEWER() {
        List<String> interviewer= new ArrayList<>();
        interviewer.addAll(USER);
        interviewer.addAll(INTERVIEWER);
        return interviewer;
    }

    public static List<String> getJOBPOSTING() {
        return JOBPOSTING;
    }

    public static List<String> getAPPLICATION() {
        return APPLICATION;
    }

    public static List<String> getINTERVIEW() {
        return INTERVIEW;
    }

    public static List<String> getDOCUMENT() {
        return DOCUMENT;
    }




    public static HashMap<String, String> template(Object obj) {
        if (obj.getClass() == Company.class) {
            return template((Company) obj);
        } else if (obj.getClass() == User.class) {
            return template((User) obj);
        } else if (obj.getClass() == Applicant.class) {
            return template((Applicant) obj);
        } else if (obj.getClass() == HumanResource.class) {
            return template((HumanResource) obj);
        } else if (obj.getClass() == Interviewer.class) {
            return template((Interviewer) obj);
        } else if (obj.getClass() == JobPosting.class) {
            return template((JobPosting) obj);
        } else if (obj.getClass() == Interview.class) {
            return template((Interview) obj);
        } else if (obj.getClass() == Application.class) {
            return template((Application) obj);
        } else if (obj.getClass() == Document.class) {
            return template((Document) obj);
        } else {
            return null;
        }
    }

    private static HashMap<String, String> template(Company company) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("Company name", "Google");
        attributes.put("Company id", "123");
        attributes.put("Number of interviewers", "3000");
        attributes.put("Number of current job postings", "678");
        attributes.put("Number of all job postings", "9870023");
        attributes.put("Number of applicants currently applied", "2145");
        return attributes;
    }

    private static HashMap<String, String> template(User user) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("User name", "Kerwin");
        attributes.put("Password", "OhShit");
        attributes.put("Date created", "2019-07-06");
        return attributes;
    }

    private static HashMap<String, String> template(Applicant applicant) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.putAll(ToString.template((User) applicant));
        attributes.put("Real name", "Yuchao Yi");
        attributes.put("Applicant id", "100");
        attributes.put("Number of documents", "12");
        attributes.put("Number of upcoming interviews", "3");
        attributes.put("Number of current applications", "4");
        attributes.put("Number of all applications", "9");
        return attributes;
    }

    private static HashMap<String, String> template(HumanResource humanResource) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.putAll(ToString.template((User) humanResource));
        attributes.put("Company name", "Google");
        attributes.put("HumanResource id", "432");
        return attributes;
    }

    private static HashMap<String, String> template(Interviewer interviewer) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.putAll(ToString.template((User) interviewer));
        attributes.put("Real name", "Yuchao Yi");
        attributes.put("Interviewer id", "89012");
        attributes.put("Company name", "Google");
        attributes.put("Number of upcoming interviews", "32");
        return attributes;
    }

    private static HashMap<String, String> template(JobPosting jobPosting) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("JobPosting id", "435");
        attributes.put("Company name", "Google");
        attributes.put("Date posted", "2019-04-01");
        attributes.put("Date closed", "2019-08-01");
        attributes.put("Current status", "Open");
        attributes.put("Number of positions", "2");
        attributes.put("Requirements", "1 CV + 1 cover letter + at least 20 years old + driver's licence");
        attributes.put("Rounds of interviews", "Phone -> In-person 1 -> In-person 2 -> In-person 3");
        attributes.put("Current round", "Not started yet");
        attributes.put("Number of all applicants", "325");
        attributes.put("Number of remaining applicants", "325");
        return attributes;
    }

    private static HashMap<String, String> template(Application application) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("Application id", "1170");
        attributes.put("Applicant real name", "Yuchao Yi");
        attributes.put("JobPosting id", "435");
        attributes.put("Documents", "1 CV + 1 cover letter");
        return attributes;
    }

    private static HashMap<String, String> template(Interview interview) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("Interview id", "78303");
        attributes.put("Date", "2019-09-01");
        attributes.put("Location", "BA1150");
        attributes.put("Duration", "1.0 hour");
        attributes.put("Applicant real name", "Yuchao Yi");
        attributes.put("Interviewer real name", "Joseph Repka");
        attributes.put("JobPosting id", "435");
        attributes.put("Application id", "1170");
        attributes.put("Round", "In-person 1");
        attributes.put("Status", "PENDING");
        attributes.put("Recommendation", "The interviewer has not updated recommendation yet.");
        return attributes;
    }

    private static HashMap<String, String> template(Document document) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("Document id", "80076");
        attributes.put("Applicant real name", "Yuchao Yi");
        attributes.put("Applicant id", "100");
        attributes.put("Document name", "cover letter");
        attributes.put("Content", "Something about cover letter\nthat is from a\ntxt file");
        return attributes;
    }

}
