package domain.applying;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentManager implements Serializable {

    private HashMap<String, Document> documents = new HashMap<>();
    private boolean editable;


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
