package domain.applying;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentManager {

    private HashMap<String, Document> documents;
    private boolean editable;


    public DocumentManager(boolean editable) {
        this.documents = new HashMap<>();
        this.editable = editable;
    }

    public Document findDocument(String docName) {
        return this.documents.get(docName);
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setToBeEditable() {
        this.editable = true;
    }

    public void addDocument(String docName, Document document) {
        this.documents.put(docName, document);
    }

    public boolean removeDocument(Document document) {
        for (String docName: this.documents.keySet()) {
            if (this.documents.get(docName).equals(document)) {
                this.documents.remove(docName);
                return true;
            }
        }
        return false;
    }

    public boolean removeDocument(String docName) {
        if (this.documents.containsKey(docName)) {
            this.documents.remove(docName);
            return true;
        }
        return false;
    }

    public ArrayList<Document> getAllDocuments() {
        return (ArrayList<Document>) this.documents.values();
    }

    public ArrayList<String> getAllDocNames() {
        return (ArrayList<String>) this.documents.keySet();
    }

    public int getNumOfDocuments() {
        return this.documents.size();
    }

    public void updateAllDocuments(LocalDate curentDate) {
        HashMap<String, Document> remainingDocuments = new HashMap<>();
        Document document;
        for (String docName: this.documents.keySet()) {
            document = this.documents.get(docName);
            if (!document.getLastUsedDate().isBefore(curentDate)) {
                remainingDocuments.put(docName, document);
            }
        }
        this.documents = remainingDocuments;
    }

}
