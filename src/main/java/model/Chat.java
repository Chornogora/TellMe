package model;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private SimpleUser creator;
    private List<Message> messages;
    private String theme;

    public Chat(SimpleUser user, String theme){
        this.creator = user;
        this.theme = theme;
        this.messages = new ArrayList<>();
    }
}
