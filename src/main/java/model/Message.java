package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class Message {
    private Chat chat;
    private Timestamp sentDatetime;
    private SimpleUser sender;
    private String text;

    public Message(Chat chat, SimpleUser user, String text){
        this.chat = chat;
        this.sender = user;
        this.text = text;
        this.sentDatetime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}
