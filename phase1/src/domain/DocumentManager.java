package domain;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


import static java.time.temporal.ChronoUnit.DAYS;

class DocumentManager {
    private HashMap<LocalDate, ArrayList<Applicant>> deleteAfterThirtyDays;
    private HashMap<String, ArrayList<File>> attachedDocuments;  //application to files
    private HashMap<String, ArrayList<File>> allDocuments;  // applicant to files
    private HashMap<String, File> allFiles;
    private static DocumentManager documentManager = null;



    public DocumentManager() {
        deleteAfterThirtyDays = new HashMap<>();
        attachedDocuments = new HashMap<>();
        allDocuments = new HashMap<>();
        allFiles = new HashMap<>();
    }


    //todo: deal with exceptions in this class
    String viewDocument(String fileName) {
//        fileName is the full path of the file
        File file = allFiles.get(fileName);
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

    public String createNewDocument(String Filename, String content, String applicant) throws IOException {
        boolean isExisted = false;
        File file = new File(Filename);
        if (file.createNewFile()) {
            writeDoc(file, content);
            allFiles.put(Filename, file);
            addToAllDocuments(applicant, file);
            return "A document has been added to your documents.";
        } else {
            return "File already existed!";
        }
    }

    public String deleteDocument(String fileName) {
        File file = allFiles.get(fileName);
        allFiles.remove(fileName);
        if (file.delete()) {
            return "File is deleted successfully";
        } else {
            return"Failed to delete the document: " + fileName;
        }
    }

    public void updateDocument(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(allFiles.get(fileName), false))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //写下return的string
    }

    public void deleteAllInactiveDocuments() {
        for (LocalDate addedDate : deleteAfterThirtyDays.keySet()) {
            if (DAYS.between(addedDate, LocalDate.now()) > 30) {
                deleteAfterThirtyDays.remove(addedDate);
            }
        }
    }

    public void addToMap(String keyToAdd, File newValue, HashMap<String, ArrayList<File>> map) {
        if (map.containsKey(keyToAdd)) {
            map.get(keyToAdd).add(newValue);
        } else {
            ArrayList<File> values = new ArrayList<>();
            values.add(newValue);
            map.put(keyToAdd, values);
        }
    }

    public void addToAllDocuments(String Applicant, File file) {
        addToMap(Applicant, file, this.allDocuments);
    }

    public void addToAttachedDocuments(String Application, File file) {
        addToMap(Application, file, this.attachedDocuments);
    }



    public void addToDeleteAfterThirtyDays(Applicant applicant) {
        ArrayList<Applicant> applicants;
        if (deleteAfterThirtyDays.containsKey(LocalDate.now())) {
            applicants = deleteAfterThirtyDays.get(LocalDate.now());
        } else {
            deleteAfterThirtyDays.put(LocalDate.now(), (applicants=new ArrayList<>()));
        }
        applicants.add(applicant);
    }

    public void removeFromDeleteAfterThirtyDays(Applicant applicant) {
        ArrayList<Applicant> lst;
        for (LocalDate addedDate : deleteAfterThirtyDays.keySet()) {
            if ((lst = deleteAfterThirtyDays.get(addedDate)).contains(applicant)) {
                lst.remove(applicant);
                break;
            }
        }
    }

}