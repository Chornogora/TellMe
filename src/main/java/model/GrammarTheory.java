package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="grtheory")
public class GrammarTheory extends Task{

    //html format
    @Column(name = "grtheory_content")
    private String content;

    public GrammarTheory(int number, String content){
        super(number);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}