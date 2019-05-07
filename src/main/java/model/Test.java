package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tests")
public class Test extends Task{

    private enum TYPES{
        ONE_CORRECT, SEVERAL_CORRECT, WRITE_WORD
    }

    @Enumerated(EnumType.STRING)
    @Column(name="test_type")
    private TYPES type;

    @Column(name="test_picture")
    private String testPicture;

    @Column(name="test_text")
    private String testText;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    private List<Variant> variants;

    public Test(String testPictureLink, String testText, String type){
        this.testPicture = testPictureLink;
        this.testText = testText;
        this.type = TYPES.valueOf(type);
        variants = new ArrayList<>();
    }

    public Test(String testPictureLink, String testText, String type, List<Variant> lst){
        this.testPicture = testPictureLink;
        this.testText = testText;
        this.type = TYPES.valueOf(type);
        variants = lst;
    }

    public List<Variant> getVariants() {
        return variants;
    }
}
