package domain.applying;

import domain.filter.Filterable;
import domain.show.ShowAble;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

public class Document implements Filterable, Serializable, ShowAble {

    private String documentName = "";
    private String content;
    private LocalDate lastUsedDate;
    private boolean isUsed;

    // For testing purpose
    public Document(String docName, String content) {
        this.documentName = docName;
        this.content = content;
        this.lastUsedDate = LocalDate.now();
        this.isUsed = false;
    }

    public Document(String path) {
        File file = new File(path);
        this.lastUsedDate = LocalDate.now();
        if (file.exists()) {
            documentName = file.getName();
            content = readContent(file);
            setUsed();
            update();
        }
    }

    public static void main(String[] args) {
        // Todo: warning, path not complete.
        Document document = new Document("CV.txt");
        System.out.println(document.getContent());
        Document document1 = new Document("Cover.txt");
        System.out.println(document1.getContent());
    }

    private String readContent(File file) {
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

    // Get current date. Can rewrite by a self designed Date Time System if desired.
    private LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getContent() {
        return this.content;
    }

    public LocalDate getLastUsedDate() {
        return this.lastUsedDate;
    }

    public void setUsed() {
        isUsed = true;
    }

    private void clearUsage() {
        isUsed = false;
    }

    public void update() {
        if (isUsed) {
            clearUsage();
            lastUsedDate = getCurrentDate();
        }
    }

    public boolean shouldDelete() {
        return getLastUsedDate().plusDays(30).isBefore(getCurrentDate());
    }

    @Override
    public String toString() {
        return getInfoString("Name", documentName) +
                getInfoString("Last used date", lastUsedDate.toString()) +
                getInfoString("Content", content);
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("last used date", getLastUsedDate().toString());
        map.put("document name", getDocumentName());
        return map;
    }

}
