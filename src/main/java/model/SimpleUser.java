package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SimpleUser extends User {
    //link to image
    private String avatar;

    private Date birthday;

    private Levels level;

    private int points;

    private Date signUpDate;

    private List<Notification> notifications;

    private List<Progress> progresses;

    public SimpleUser(String login, String pass, String mail){
        super(login, pass, mail);
        points = 0;
        level = Levels.getLevelByPoints(points);
        signUpDate = new Date(Calendar.getInstance().getTimeInMillis());
        notifications = new ArrayList<>();
        progresses = new ArrayList<>();
    }

    public SimpleUser(String login, String pass, String mail, String ava){
        this(login, pass, mail);
        this.avatar = ava;
    }

    public SimpleUser(String login, String pass, String mail, Date birthday){
        this(login, pass, mail);
        this.birthday = birthday;
    }

    public SimpleUser(String login, String pass, String mail, String ava, Date birthday){
        this(login, pass, mail, ava);
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
        return level;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}