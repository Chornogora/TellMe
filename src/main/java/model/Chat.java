package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private SimpleUser creator;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<Message> messages;

    @Column(name = "chat_theme")
    private String theme;

    public Chat(){

    }

    public Chat(SimpleUser user, String theme){
        this.creator = user;
        this.theme = theme;
        this.messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }
}
