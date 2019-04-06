package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ltheory")
public class LexicalTheory extends Task{

    @OneToMany(mappedBy = "theory", fetch = FetchType.LAZY)
    private List<Word> words;

    public LexicalTheory(){

    }

    public LexicalTheory(int number){
        super(number);
        this.words = new ArrayList<>();
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}