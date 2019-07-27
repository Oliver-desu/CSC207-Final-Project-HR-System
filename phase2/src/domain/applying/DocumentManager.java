package domain.applying;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentManager {

    private HashMap<String, Document> documents;
    private boolean editable;


    public DocumentManager() {}

    public DocumentManager(boolean editable) {
        this.documents = new HashMap<>();
        this.editable = editable;
    }

    public Document findDocument(String docName) {
        return this.documents.get(docName);
    }

    private boolean isEditable() {
        return this.editable;
    }

    void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean addDocument(String docName, Document document) {
        if (this.isEditable() && !this.documents.containsKey(docName)) {
            this.documents.put(docName, document);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeDocument(Document document) {
        // removeDocument can not deal with situation where two identical documents are submitted with different
        // docNames, currently it just deletes whichever one comes first
        if (this.isEditable()) {
            for (String docName : this.documents.keySet()) {
                if (this.documents.get(docName).equals(document)) {
                    this.documents.remove(docName);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean removeDocument(String docName) {
        if (this.editable && this.documents.containsKey(docName)) {
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

    public void updateAllDocuments(LocalDate currentDate) {
        if (this.isEditable()) {
            HashMap<String, Document> remainingDocuments = new HashMap<>();
            Document document;
            LocalDate deleteDate;
            for (String docName : this.documents.keySet()) {
                document = this.documents.get(docName);
                deleteDate = document.getLastUsedDate().plusDays(30);
                if (!deleteDate.isAfter(currentDate)) {
                    remainingDocuments.put(docName, document);
                }
            }
            this.documents = remainingDocuments;
        }
    }

}
