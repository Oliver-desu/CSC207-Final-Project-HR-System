package domain.applying;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code DocumentManager} deals with all the documents for either
 * an {@code Applicant} or an {@code Application}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see     domain.user.Applicant
 * @see     Application
 * @since 2019-08-04
 */
public class DocumentManager implements Serializable {

    /**
     * A hash map where the key is document name and value is the corresponding document.
     *
     * @see #getAllDocuments()
     * @see #getAllDocNames()
     * @see #addDocument(Document)
     * @see #findDocument(String)
     * @see #removeDocument(Document)
     * @see #removeDocument(String)
     */
    private HashMap<String, Document> documents = new HashMap<>();

    /**
     * True if and only if the holder is able to add/remove document.
     * That happens if the holder is {@code Applicant} or if the holder
     * is {@code Application} and it has not been submitted yet.
     *
     * @see     #isEditable()
     * @see     #setEditable(boolean)
     */
    private boolean editable;


    /**
     * Create a new document manager.
     * @param editable  determines whether the holder is allowed to modify documents
     */
    public DocumentManager(boolean editable) {
        this.editable = editable;
    }

    public Document findDocument(String docName) {
        return this.documents.get(docName);
    }

    private boolean isEditable() {
        return this.editable;
    }

    private boolean isValid(Document document) {
        return !document.getDocumentName().equals("") && !documents.values().contains(document);
    }

    void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean addDocument(Document document) {
        if (this.isEditable() && isValid(document)) {
            this.documents.put(document.getDocumentName(), document);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeDocument(Document document) {
        if (documents.values().contains(document)) {
            return removeDocument(document.getDocumentName());
        }
        return false;
    }

    private boolean removeDocument(String docName) {
        if (isEditable() && this.documents.containsKey(docName)) {
            this.documents.remove(docName);
            return true;
        }
        return false;
    }

    public ArrayList<Document> getAllDocuments() {
        return new ArrayList<>(this.documents.values());
    }

    public ArrayList<String> getAllDocNames() {
        return new ArrayList<>(this.documents.keySet());
    }

    public int getNumOfDocuments() {
        return this.documents.size();
    }

    public void updateAllDocuments() {
        for (Document document : getAllDocuments()) {
            document.update();
            if (document.shouldDelete()) removeDocument(document);
        }
    }
}
