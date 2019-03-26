package model;

public class Word {
    private String description;

    private String name;
    //just a link
    private String picture;

    private String translation;

    public Word(String desc, String name, String trans){
        this.description = desc;
        this.name = name;
        this.translation = trans;
    }

    public Word(String desc, String name, String trans, String picture){
        this(desc, name, trans);
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
}