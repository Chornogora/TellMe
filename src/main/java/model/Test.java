package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tests")
public class Test extends Task{

    @Column(name="test_picture")
    private String testPicture;

    @Column(name="test_text")
    private String testText;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    private List<Variant> variants;

    public Test(String testPictureLink, String testText){
        this.testPicture = testPictureLink;
        this.testText = testText;
        variants = new ArrayList<>();
    }

    public Test(String testPictureLink, String testText, List<Variant> lst){
        this.testPicture = testPictureLink;
        this.testText = testText;
        variants = lst;
    }

    public List<Variant> getVariants() {
        return variants;
    }
}
