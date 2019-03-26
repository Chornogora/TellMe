package model;

public class GrammarTheory extends Task{
    //html format
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GrammarTheory(String text){
        this.content = text;
    }
}
