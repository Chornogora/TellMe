package model;

import java.util.ArrayList;
import java.util.List;

public class Test extends Task{

    private String testPicture;

    private String testText;

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

}
