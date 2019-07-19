package domain.applying;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

class InterviewInfo {

    private String interviewerId;
    private LocalDateTime time;
    private String location;
    private int duration;


    public InterviewInfo(HashMap<String, String> values) {
        this.interviewerId = values.get("interviewerId");
        this.time = LocalDateTime.parse(values.get("time"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.location = values.get("location");
        this.duration = Integer.parseInt(values.get("duration"));
    }

    public String getInterviewerId() {
        return this.interviewerId;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getLocation() {
        return this.location;
    }

    public int getDuration() {
        return this.duration;
    }
}