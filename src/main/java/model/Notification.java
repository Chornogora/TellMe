package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notification_id")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_generatedTimestamp")
    private Timestamp generatedTimestamp;

    @Column(name = "notification_isviewed")
    private boolean isViewed;

    @Column(name = "notification_text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SimpleUser user;

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