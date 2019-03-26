package model;

public class Variant {
    private int number;

    private String rightEquivalent;

    private String text;

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
}
