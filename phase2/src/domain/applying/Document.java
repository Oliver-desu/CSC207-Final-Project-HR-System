package domain.applying;

import domain.filter.Filterable;
import domain.show.ShowAble;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Class {@code Document} is used by {@code Applicant} when applying for a
 * {@code JobPosting} as required by the company.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see domain.user.Applicant
 * @see domain.job.JobPosting
 * @see Application
 * @since 2019-08-04
 */
public class Document implements Filterable, Serializable, ShowAble {

    /**
     * The title of the document.
     *
     * @see #getDocumentName()
     */
    private String documentName = "";

    /**
     * The content of the document.
     *
     * @see #getContent()
     * @see #readContent(File)
     */
    private String content;

    /**
     * Last date that this document is used for any application.
     * Any document that has not been used for the past 30 days will
     * be deleted automatically.
     *
     * @see #getLastUsedDate()
     * @see #update()
     * @see #shouldDelete()
     */
    private LocalDate lastUsedDate;

    /**
     *
     */
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
