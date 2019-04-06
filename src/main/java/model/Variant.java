package model;

import javax.persistence.*;

@Entity
@Table(name = "variants")
public class Variant {

    @Id
    @Column(name = "variant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "variant_number")
    private int number;

    @Column(name = "variant_rightequivalent")
    private String rightEquivalent;

    @Column(name = "variant_text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Test test;

    //Empty constructor for Hibernate
    public Variant(){

    }

    public Variant(int num, String right, String text){
        this.number = num;
        this.rightEquivalent = right;
        this.text = text;
    }

    public boolean isRight(){
        return this.text.equals(this.rightEquivalent);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setRightEquivalent(String right){
        this.rightEquivalent = right;
    }

    public String getRightEquivalent(){
        return rightEquivalent;
    }

    public void setText(String text){
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}