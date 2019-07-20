package domain.applying;

import java.time.LocalDate;

public class Document {

    private String content;
    private LocalDate lastUsedDate;


    public Document(String content) {
        this.content = content;
        this.lastUsedDate = LocalDate.now();
    }

    public LocalDate getLastUsedDate() {
        return this.lastUsedDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateLastUsedDate(LocalDate currentDate) {
        this.lastUsedDate = currentDate;
    }

}
