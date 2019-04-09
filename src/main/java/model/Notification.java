package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notification_id")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_generatedTimestamp")
    private Calendar generatedTimestamp;

    @Column(name = "notification_isviewed")
    private boolean isViewed;

    @Column(name = "notification_text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SimpleUser user;

    public Notification(){

    }

    public Notification(Calendar timestamp, String text){
        this.generatedTimestamp = timestamp;
        this.text = text;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public Calendar getGeneratedTimestamp() {
        return generatedTimestamp;
    }

    public String getText() {
        return text;
    }
}