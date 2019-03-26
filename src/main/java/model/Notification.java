package model;

import java.sql.Timestamp;

public class Notification {
    private Timestamp generatedTimestamp;

    private boolean isViewed;

    private String text;

    public Notification(Timestamp timestamp, String text){
        this.generatedTimestamp = timestamp;
        this.text = text;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public Timestamp getGeneratedTimestamp() {
        return generatedTimestamp;
    }

    public String getText() {
        return text;
    }
}