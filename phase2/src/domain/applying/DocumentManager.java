package domain.applying;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentManager {

    private HashMap<String, Document> documents;
    private boolean editable;


    public DocumentManager() {

    }

    public Document findDocument(String docName) {
        return null;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setToBeEditable() {

    }

    public void addDocument(Document document) {

    }

    public boolean removeDocument(Document document) {
        return true;
    }

    public boolean removeDocument(String docName) {
        return true;
    }

    public ArrayList<Document> getAllDocuments() {
        return null;
    }

    public ArrayList<String> getAllDocNames() {
        return null;
    }

    public int getNumOfDocuments() {
        return 0;
    }

    public void updateAllDocuments(LocalDate curentDate) {

    }

}
