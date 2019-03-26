package model;

public class Administrator extends User{
    private int accessLevel;

    public Administrator(String login, String pass, String mail, int access){
        super(login, pass, mail);
        this.accessLevel = access;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
