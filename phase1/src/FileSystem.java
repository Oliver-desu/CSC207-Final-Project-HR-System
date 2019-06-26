import java.io.*;
import java.util.*;
import java.nio.file.Paths;

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


    public static void main(String[] args) throws Exception {
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

    private static File pathToFile(String directory, String id) {
        return Paths.get(MAIN_DIR, directory, id).toFile();
    }

    private static File pathToFile(File directory, String file) {
        return Paths.get(directory.toString(), file).toFile();
    }

    private static File getAccount(File file) {
        return pathToFile(file, ACCOUNT);
    }

    private static HashMap<String, String> readAccount(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, String> attributes = new HashMap<>();
        String line;
        while (!(line = reader.readLine()).startsWith(EOF)) {
            String[] strings = line.split(REGEX);
            attributes.put(strings[0], strings[1]);
        }
        return attributes;
    }

    private static void writeAccount(File file, HashMap<String, String> accountMap) throws Exception {
        FileWriter writer = new FileWriter(file.getAbsoluteFile());
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Map.Entry<String, String> entry : accountMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + REGEX + entry.getValue() + "\n");
            }
            bufferedWriter.write(EOF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readCollection(File file) {
        List<String> lst = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                lst.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    private static void writeCollection(File file, ArrayList<String> collections) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            for (String element : collections) {
                bufferedWriter.write(element + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendCollection(File file, String collection) {
        try (FileWriter writer = new FileWriter(file.getAbsoluteFile(), true)) {
            writer.write(collection + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readDoc(File file) {
        StringBuffer content = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line.trim());
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static void writeDoc(File file, String doc) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bw.write(doc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File getApplicant(String username) {
        return pathToFile(APPLICANTS, username);
    }

    static File getApplication(String id) {
        return pathToFile(APPLICATIONS, id);
    }

    static File getCompany(String name) {
        return pathToFile(COMPANIES, name);
    }

    static File getHR(String username) {
        return pathToFile(HR_COORDINATORS, username);
    }

    static File getInterviewer(String username) {
        return pathToFile(INTERVIEWERS, username);
    }

    static File getInterview(String id) {
        return pathToFile(INTERVIEWS, id);
    }

    static File getJobPosting(String id) {
        return pathToFile(JOB_POSTINGS, id);
    }
}
