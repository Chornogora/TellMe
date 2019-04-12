package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class SimpleUser extends User {
    //link to image
    @Column(name = "user_avatar")
    private String avatar;

    @Column(name = "user_birthday")
    private Date birthday;

    @Transient
    private Levels level;

    @Column(name = "user_points")
    private int points;

    @Temporal(TemporalType.DATE)
    @Column(name = "user_signupdate")
    private Date signUpDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    transient private List<Notification> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    transient private List<Progress> progresses;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    transient private List<Chat> chats;

    public SimpleUser(){

    }

    public SimpleUser(String login, String pass, String mail){
        super(login, pass, mail);
        points = 0;
        level = Levels.getLevelByPoints(points);
        signUpDate = new Date(Calendar.getInstance().getTimeInMillis());
        notifications = new ArrayList<>();
        progresses = new ArrayList<>();
        chats = new ArrayList<>();
    }

    public SimpleUser(String login, String pass, String mail, Date birthday){
        this(login, pass, mail);
        this.birthday = birthday;
    }

    public void setPoints(int points){
        this.points = points;
        this.level = Levels.getLevelByPoints(points);
    }

    public int getPoints(){
        return points;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Levels getLevel() {
        if(level == null) {
            level = Levels.getLevelByPoints(points);
        }
        return level;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}