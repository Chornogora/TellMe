package model;

import javax.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name="user_login")
    private String login;

    @Column(name="user_password")
    private String password;

    @Column(name="user_email")
    private String email;

    User(){

    }

    User(String login, String pass, String mail){
        this.login = login;
        this.password = pass;
        this.email = mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return util.Encoder.decode("password");
    }

    public void setPassword(String password) {
        this.password = util.Encoder.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
