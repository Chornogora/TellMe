package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_id")
    private Chat chat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "message_senttimestamp")
    private Timestamp sentTimestamp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private SimpleUser sender;

    @Column(name="message_text")
    private String text;

    public Message(Chat chat, SimpleUser user, String text){
        this.chat = chat;
        this.sender = user;
        this.text = text;
        this.sentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}
