package domain.applying;

import domain.filter.Filterable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Document implements Filterable {

    private String documentName;
    private String content;
    private LocalDate lastUsedDate;
    private boolean isUsed;

    // Todo
    public Document(String path) {
        documentName = path;
        content = path + path;
        setUsed();
        update();
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
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("Last used date");
        headings.add("Document name");
        return headings.toArray(new String[2]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(getLastUsedDate().toString());
        values.add(getDocumentName());
        return values.toArray(new String[2]);
    }
}
