package model;

public abstract class User {
    private String login;

    private String password;

    private String email;

    public User(String login, String pass, String mail){
        this.login = login;
        this.password = pass;
        this.email = mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
