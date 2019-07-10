package domain;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class DocumentManager {
    private HashMap<LocalDate, ArrayList<Applicant>> deleteAfterThirtyDays;
    private HashMap<String, ArrayList<File>> attachedDocuments;  //application to files
    private HashMap<String, ArrayList<File>> allDocuments;  // applicant to files
    private HashMap<String, File> allFiles;
    private static DocumentManager documentManager = null;


    public static DocumentManager getInstance() {
        if (documentManager == null) {
            documentManager = new DocumentManager();
        }
        return documentManager;
    }

    private DocumentManager(){
        deleteAfterThirtyDays = new HashMap<>();
        attachedDocuments = new HashMap<>();
        allDocuments = new HashMap<>();
        allFiles = new HashMap<>();
    }


//todo: deal with exceptions in this class
    String viewDocument(File file) {
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

    private static void writeDoc(File file, String doc) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bufferedWriter.write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createNewDocument(String Filename, String content, String Applicant)throws IOException {
        boolean isExisted = false;
        File file = new File(Filename);
        if(file.createNewFile()){
            writeDoc(file, content);
            allFiles.put(Filename, file);
            addToAllDocuments(Applicant, file);
//todo:            deleteAfterThirtyDays.put(LocalDate.now(), ); //Arraylist<applicant>
        }else{
            System.out.println("File already existed!");
        }
    }

    void deleteDocument(String fileName) {
        File file = allFiles.get(fileName);
        allFiles.remove(fileName);
        if(file.delete())
        {
            System.out.println("File is deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the document: " + fileName);
        }
    }

    void updateDocument(String fileName, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(allFiles.get(fileName), false))) {
            bufferedWriter.write(content);}
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void deleteAllInactiveDocuments() {
//todo
    }

    private void addToMap(String key, File file, HashMap<String, ArrayList<File>> map){
        ArrayList<File> files;
        if(map.containsKey(key)) {
            files = map.get(key);
            files.add(file);
        }else{
            files = new ArrayList<>();
            files.add(file);
            map.put(key, files);
        }
    }

    private void addToAllDocuments(String Applicant, File file){
        addToMap(Applicant, file, this.allDocuments);
    }

    void addToAttachedDocuments(String Application, File file){
        addToMap(Application, file, this.attachedDocuments);
    }


    void removeFromDeleteAfterThirtyDays(Applicant applicant) {
        ArrayList<Applicant> lst;
        if(deleteAfterThirtyDays.containsValue(applicant)){
            for(LocalDate closeDate: deleteAfterThirtyDays.keySet()){
                if((lst=deleteAfterThirtyDays.get(closeDate)).contains(applicant)){
                    lst.remove(applicant);
                }
            }
        }
    }


}