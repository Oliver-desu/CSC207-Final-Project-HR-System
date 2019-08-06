package domain.applying;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code DocumentManager} deals with all the documents for either
 * an {@code Applicant} or an {@code Application}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see domain.user.Applicant
 * @see Application
 * @since 2019-08-04
 */
public class DocumentManager implements Serializable {

    private static final long serialVersionUID = -1699054066935007390L;

    /**
     * A hash map where the key is document name and value is the corresponding document.
     *
     * @see #getAllDocuments()
     * @see #getAllDocNames()
     * @see #addDocument(Document)
     * @see #findDocument(String)
     * @see #removeDocument(Document)
     */
    private HashMap<String, Document> documents = new HashMap<>();

    /**
     * True if and only if the holder is able to add/remove document.
     * That happens if the holder is {@code Applicant} or if the holder
     * is {@code Application} and it has not been submitted yet.
     *
     * @see #isEditable()
     * @see #setEditable(boolean)
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

    /**
     * A new document is valid if and only if the name is not empty and it has not
     * been added to this document manager already.
     *
     * @param document a new document for this document manager
     * @return true if and only if the name is not empty and it has not been added
     * to this document manager already
     * @see #addDocument(Document)
     */
    private boolean isValid(Document document) {
        return !document.getDocumentName().equals("") && !documents.values().contains(document);
    }

    void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Add document if and only if this document is valid and holder is allowed to
     * modify document manager.
     * @param document a new document to add to this manager
     * @return true if and only if this document is valid and holder is allowed to
     * modify document manager
     * @see #isEditable()
     * @see #isValid(Document)
     */
    public boolean addDocument(Document document) {
        if (this.isEditable() && isValid(document)) {
            this.documents.put(document.getDocumentName(), document);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove and document and return true if and only if the holder is allowed
     * to modify the manager and this manager has this document.
     * @param document the name of the document wished to remove
     * @return true if and only if the holder is allowed
     * to modify the manager and this manager has this document.
     */
    public boolean removeDocument(Document document) {
        String docName = document.getDocumentName();
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

    /**
     * Update all the documents it currently hold.
     * @see Document#update()
     * @see Document#shouldDelete()
     */
    public void updateAllDocuments() {
        for (Document document : getAllDocuments()) {
            document.update();
            if (document.shouldDelete()) removeDocument(document);
        }
    }
}
