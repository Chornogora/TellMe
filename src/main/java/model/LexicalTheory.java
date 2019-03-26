package model;

import java.util.ArrayList;
import java.util.List;

public class LexicalTheory extends Task{
    private List<Word> words;

    public LexicalTheory(List<Word> lst){
        this.words = lst;
    }

    public LexicalTheory(){
        this.words = new ArrayList<>();
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}