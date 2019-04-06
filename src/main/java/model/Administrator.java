package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="administrators")
public class Administrator extends User{

    @Column(name="administrator_access")
    private int accessLevel;

    public Administrator(){

    }

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
