package domain.applying;

import domain.filter.Filterable;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Document implements Filterable {

    private String documentName = "???";
    private String content;
    private LocalDate lastUsedDate;


    public Document() {
        this.content = "This is a CV";
        this.lastUsedDate = LocalDate.now();
    }

    // Todo
    public Document(Path path) {

    }

    public Document(String content) {
        this.content = content;
        this.lastUsedDate = LocalDate.now();
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public void updateLastUsedDate(LocalDate currentDate) {
        this.lastUsedDate = currentDate;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("lastUsedDate");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.lastUsedDate.toString());
        return values.toArray(new String[0]);
    }
}
