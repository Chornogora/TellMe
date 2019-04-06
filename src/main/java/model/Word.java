package model;

import javax.persistence.*;

@Entity
@Table(name="words")
public class Word {

    @Id
    @Column(name = "word_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "word_description")
    private String description;

    @Column(name = "word_name")
    private String name;

    //just a link
    @Column(name = "word_picture")
    private String picture;

    @Column(name = "word_translation")
    private String translation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private LexicalTheory theory;

    //Empty constructor for Hibernate
    public Word(){

    }

    public Word(String name, String desc, String trans){
        this.description = desc;
        this.name = name;
        this.translation = trans;
    }

    public Word(String name, String desc, String trans, String picture){
        this(name, desc, trans);
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public LexicalTheory getTheory() {
        return theory;
    }

    public void setTheory(LexicalTheory theory) {
        this.theory = theory;
    }
}