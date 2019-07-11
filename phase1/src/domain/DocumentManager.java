package domain;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


import static java.time.temporal.ChronoUnit.DAYS;

class DocumentManager implements Serializable {
    private HashMap<LocalDate, ArrayList<Applicant>> deleteAfterThirtyDays;
    private HashMap<String, ArrayList<File>> attachedDocuments;  //application to files
    private HashMap<String, ArrayList<File>> allDocuments;  // applicant to files
    private HashMap<String, File> allFiles;

    public DocumentManager() {
        deleteAfterThirtyDays = new HashMap<>();
        attachedDocuments = new HashMap<>();
        allDocuments = new HashMap<>();
        allFiles = new HashMap<>();
    }

    //todo: deal with exceptions in this class

    String viewDocument(String fileName, String application) {
        File file;
        if (attachedDocuments.get(application).contains((file = allFiles.get(fileName)))) {
            return getContent(file);
        } else {
            return "No such file exists.";
        }
    }

    private String getContent(File file) {
//        fileName is the full path of the file
        StringBuilder content = new StringBuilder();
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

    ArrayList<File> getAttachedDocuments(String Application) {
        return attachedDocuments.get(Application);
    }

    ArrayList<File> getMyDocuments(String Applicant) {
        return allDocuments.get(Applicant);
    }

    public void writeDoc(File file, String doc) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bufferedWriter.write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createNewDocument(String fileName, String content, String applicant) throws IOException {
        if (allFiles.containsKey(fileName)) {
            return "Please try another document name.";
        } else {
            File file = new File(fileName);
            if (file.createNewFile()) {
                writeDoc(file, content);
                allFiles.put(fileName, file);
                addToAllDocuments(applicant, file);
                return "A document has been added to your documents.";
            } else {
                return "File already existed!";
            }
        }
    }

    public String deleteDocument(String fileName) {
        File file = allFiles.get(fileName);
        allFiles.remove(fileName);
        if (file.delete()) {
            return "File is deleted successfully";
        } else {
            return "Failed to delete the document: " + fileName;
        }
    }

    public String updateDocument(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(allFiles.get(fileName), false))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName + " is updated!";
    }

    public void deleteAllInactiveDocuments() {
        ArrayList<Applicant> applicants = new ArrayList<>();
        for (LocalDate addedDate : deleteAfterThirtyDays.keySet()) {
            if (DAYS.between(addedDate, LocalDate.now()) > 30) {
                applicants.addAll(deleteAfterThirtyDays.get(addedDate));
                deleteAfterThirtyDays.remove(addedDate);
            }
        }
        for (Applicant applicant : applicants) {
            for (File file : allDocuments.get(applicant.getUsername())) {
                file.delete();
            }
            allDocuments.remove(applicant.getUsername());
        }
    }

    private void addToMap(String keyToAdd, File newValue, HashMap<String, ArrayList<File>> map) {
        if (map.containsKey(keyToAdd)) {
            map.get(keyToAdd).add(newValue);
        } else {
            ArrayList<File> values = new ArrayList<>();
            values.add(newValue);
            map.put(keyToAdd, values);
        }
    }

    void addToAllDocuments(String Applicant, File file) {
        addToMap(Applicant, file, this.allDocuments);
    }

    void addToAttachedDocuments(String Application, String fileName) {
        addToMap(Application, allFiles.get(fileName), this.attachedDocuments);
    }

    void addToDeleteAfterThirtyDays(Applicant applicant) {
        ArrayList<Applicant> applicants;
        if (deleteAfterThirtyDays.containsKey(LocalDate.now())) {
            applicants = deleteAfterThirtyDays.get(LocalDate.now());
        } else {
            deleteAfterThirtyDays.put(LocalDate.now(), (applicants = new ArrayList<>()));
        }
        applicants.add(applicant);
    }

    void removeFromDeleteAfterThirtyDays(Applicant applicant) {
        ArrayList<Applicant> lst;
        for (LocalDate addedDate : deleteAfterThirtyDays.keySet()) {
            if ((lst = deleteAfterThirtyDays.get(addedDate)).contains(applicant)) {
                lst.remove(applicant);
                break;
            }
        }
    }

}