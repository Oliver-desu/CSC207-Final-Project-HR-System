package Template;

import domain.*;
import oldVersion.Document;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InterviewerToString {

/*
* Rules for using InterviewerToString:
*       When calling a method, the class being passed in must be a class within the "Attributes.txt"
*       Do not cast the object in any way, if it is an Applicant class, do not cast it to a User and call the methods
*
* How it works:
*       The validAttributes in each method decides for each class which attributes can be shown in every scenario,
*       change the specific List<String> (value) corresponding to the class (key) you want to modify in the correct
*       scenario (method).
*       For example, in UserLogin, the line
*           validAttributes.put(Company.class, Arrays.asList("Company name", "Number of interviewers"));
*       would return the information about the company's name and number of interviewers when an Interviewer gives a
*       Company instance in the user login stage.
*
* */


    public static String getAttributes(Object obj, HashMap<Class, List<String>> validAttributes) {
        HashMap<String, String> template = ToString.template(obj);
        List<String> attributes = validAttributes.get(obj.getClass());
        StringBuilder result = new StringBuilder();
        for (String attribute: attributes) {
            result.append(attribute);
            result.append(": ");
            result.append(template.get(attribute));
            result.append("\n");
        }
        return result.toString();
    }


    public static String login(Object obj) {
        HashMap<Class, List<String>> validAttributes = new HashMap<>();
        validAttributes.put(Company.class, Arrays.asList("Company name", "Number of interviewers"));
        validAttributes.put(Applicant.class, Arrays.asList("User name", "Date created", "Applicant id"));
        validAttributes.put(HumanResource.class, Arrays.asList("User name", "Date created", "Company name",
                "HumanResource id"));
        validAttributes.put(Interviewer.class, Arrays.asList("User name", "Date created", "Company name",
                "Interviewer id"));
        validAttributes.put(JobPosting.class, Arrays.asList("JobPosting id", "Rounds of interviews", "Current round"));
        validAttributes.put(Application.class, Arrays.asList("Applicant real name", "Documents"));
        validAttributes.put(Interview.class, Arrays.asList("Date", "Location", "Duration", "Applicant real name",
                "Interviewer real name", "Interview id"));
        validAttributes.put(Document.class, Arrays.asList("Document name", "Content"));
        return getAttributes(obj, validAttributes);
    }

    public static String setRecommendation(Object obj) {
        HashMap<Class, List<String>> validAttributes = new HashMap<>();
        validAttributes.put(Company.class, ToString.getCOMPANY());
        validAttributes.put(Applicant.class, ToString.getAPPLICANT());
        validAttributes.put(HumanResource.class, ToString.getHUMANRESOURCE());
        validAttributes.put(Interviewer.class, ToString.getINTERVIEWER());
        validAttributes.put(JobPosting.class, ToString.getJOBPOSTING());
        validAttributes.put(Application.class, ToString.getAPPLICATION());
        validAttributes.put(Interview.class, ToString.getINTERVIEW());
        validAttributes.put(Document.class, ToString.getDOCUMENT());
        return getAttributes(obj, validAttributes);
    }

    public static void main(String[] args) {
    }

}
