package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    public Test(){

    }

    public Test(Test test){
        this.type = test.type;
        this.testPicture = test.testPicture;
        this.testText = test.testText;
        this.variants = new ArrayList<>();
        this.variants.addAll(test.variants);
    }

    public Test(Lesson lesson, int number, String testPictureLink, String testText, String type){
        super(lesson, number);
        this.testPicture = testPictureLink;
        this.testText = testText;
        this.type = TYPES.valueOf(type);
        variants = new ArrayList<>();
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void addVariant(Variant variant){
        variants.add(variant);
    }

    public boolean isPassed(int[] numbers){
        Variant[] vars = new Variant[numbers.length];
        for(int i = 0; i < numbers.length; ++i){
            for(Variant variant : variants){
                if(variant.getNumber() == numbers[i]){
                    vars[i] = variant;
                    break;
                }
            }
        }

        return isPassed(vars);
    }

    private boolean isPassed(Variant[] vars){
        switch(this.type){
            case ONE_CORRECT:
                return vars[0].isRight();
            case SEVERAL_CORRECT:
                int numRights = 0;
                for(Variant variant : variants){
                    if(variant.isRight()){
                        numRights = 0;
                    }
                }
                if(numRights == vars.length) {
                    return Arrays.asList(vars).stream().allMatch(Variant::isRight);
                }
                return false;
            default:
                return false;
        }

    }

    public boolean isPassed(String answer){
        switch(this.type){
            case ONE_CORRECT:
            case SEVERAL_CORRECT:
                try(Scanner sc = new Scanner(answer)) {
                    List<Integer> lst = new ArrayList<>();
                    while(sc.hasNextInt()){
                        lst.add(sc.nextInt());
                    }
                    int[] numbers = lst.stream().mapToInt(x -> x).toArray();
                    return isPassed(numbers);
                }
            case WRITE_WORD:
                return variants.get(0).isRight(answer);
            default:
                return false;
        }
    }
}