import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;

public class FileSystem {
    private static final String MAIN_DIR = System.getProperty("user.dir") + "\\phase1\\data";
    private static final String APPLICANTS = "Applicants";
    private static final String APPLICATIONS = "Applications";
    private static final String COMPANIES = "Companies";
    private static final String HR_COORDINATORS = "HRCoordinators";
    private static final String INTERVIEWERS = "Interviewers";
    private static final String INTERVIEWS = "Interviews";
    private static final String JOB_POSTINGS = "Jobpostings";
    private static final String ACCOUNT = "Account";
    private static final String EOF = ">>>EOF";
    private static final String REGEX = ":";


    public static void main(String[] args) throws Exception{
        System.out.print("Applicant:    ");
        System.out.println(readAccount(getAccount(getApplicant("cheny402"))));
        System.out.print("Interview:    ");
        System.out.println(readAccount(getAccount(getInterview("00003388"))));
        System.out.print("Interviewer:   ");
        System.out.println(readAccount(getAccount(getInterviewer("biglist666"))));
        System.out.print("JobPosting:   ");
        System.out.println(readAccount(getAccount(getJobPosting("fishing327"))));
        System.out.print("Company:   ");
        System.out.println(readAccount(getAccount(getCompany("StoryTeller"))));
        System.out.print("HR:   ");
        System.out.println(readAccount(getAccount(getHR("midterm57"))));
        System.out.print("Application:   ");
        System.out.println(readAccount(getAccount(getApplication("00004444"))));
    }

    private static File pathToFile(String directory, String id){
        return Path.of(MAIN_DIR, directory, id).toFile();
    }
    private static File pathToFile(File directory, String file){
        return Path.of(directory.toString(),file).toFile();
    }
    private static File getAccount(File file){
        return pathToFile(file,ACCOUNT);
    }
    private static HashMap<String,String> readAccount(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, String> attributes = new HashMap<>();
        String line;
        while(!(line = reader.readLine()).startsWith(EOF)){
            String[] strings = line.split(REGEX);
            attributes.put(strings[0], strings[1]);
        }
        return attributes;
    }

    static File getApplicant(String username){
        return pathToFile(APPLICANTS, username);
    }
    static File getApplication(String id){
        return pathToFile(APPLICATIONS, id);
    }
    static File getCompany(String name){
        return pathToFile(COMPANIES, name);
    }
    static File getHR(String username){
        return pathToFile(HR_COORDINATORS, username);
    }
    static File getInterviewer(String username){
        return pathToFile(INTERVIEWERS, username);
    }
    static File getInterview(String id){
        return pathToFile(INTERVIEWS, id);
    }
    static File getJobPosting(String id){
        return pathToFile(JOB_POSTINGS, id);
    }
}
