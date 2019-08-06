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
     * True if and only if the document is used.
     */
    private boolean isUsed;

    // For testing purpose
    public Document(String docName, String content) {
        this.documentName = docName;
        this.content = content;
        this.lastUsedDate = LocalDate.now();
        this.isUsed = false;
    }

    /**
     * Create a new {@code Document} from the file path.
     *
     * @param path the path of this document
     * @see gui.scenarios.applicant.DocumentManageScenario
     */
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
        // todo: warning, path not complete.
        Document document = new Document("CV.txt");
        System.out.println(document.getContent());
        Document document1 = new Document("Cover.txt");
        System.out.println(document1.getContent());
    }

    /**
     * Read the content of a given file.
     *
     * @param file the {@code File} that need to be converted
     * @return the content of the file in String form
     * @see #Document(String)
     */
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

    /**
     * A helper function that returns the current date.
     *
     * @return the current date
     * @see #update()
     * @see #shouldDelete()
     */
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

    private LocalDate getLastUsedDate() {
        return this.lastUsedDate;
    }

    /**
     * Set {@code isUsed} true.
     *
     * @see #Document(String)
     */
    private void setUsed() {
        isUsed = true;
    }

    /**
     * Set {@code isUsed} false.
     *
     * @see #update()
     */
    private void clearUsage() {
        isUsed = false;
    }

    /**
     * Clear the usage and update {@code lastUsedDate} if the document is used.
     *
     * @see #Document(String)
     * @see DocumentManager#updateAllDocuments()
     */
    public void update() {
        if (isUsed) {
            clearUsage();
            lastUsedDate = getCurrentDate();
        }
    }

    /**
     * Check and return whether the document should be deleted.
     *
     * @return true only when last time the document being used is over thirty days ago
     * @see DocumentManager#updateAllDocuments()
     */
    boolean shouldDelete() {
        return getLastUsedDate().plusDays(30).isBefore(getCurrentDate());
    }


    /**
     * Overrides the method {@code toString}
     *
     * @return a string that contains basic information about the document
     * @see gui.panels.OutputInfoPanel#showDocument(Document)
     * @see gui.scenarios.recruiter.ApplicationScenario
     */
    @Override
    public String toString() {
        return getInfoString("Name", documentName) +
                getInfoString("Last used date", lastUsedDate.toString()) +
                getInfoString("Content", content);
    }

    /**
     * Return a hash map of headings and corresponding values about this document.
     *
     * @return a hash map of headings and corresponding values about this document
     */
    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("last used date", getLastUsedDate().toString());
        map.put("document name", getDocumentName());
        return map;
    }

}
