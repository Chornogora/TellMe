package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum Levels {
    ELEMENTARY(getPoints(0)), BEGINNER(getPoints(1)), PREINTERMEDIATE(getPoints(2)), INTERMEDIATE(getPoints(3)), UPPERINTERMEDIATE(getPoints(4)), ADVANCED(getPoints(5)), MASTER(getPoints(6));

    Levels(int i){

    }

    public int getMinPoints(){
        return getPoints(this.ordinal());
    }

    private static String allPoints;

    private static int getPoints(int ordinal){
        if(allPoints == null){
            allPoints = getAllPoints();
        }
        String points = allPoints.split(" ")[ordinal];
        return Integer.valueOf(points);
    }

    public static Levels getLevelByPoints(int points){

        if(points < getPoints(1)){
            return ELEMENTARY;
        }else if(points < getPoints(2)){
            return BEGINNER;
        }else if(points < getPoints(3)){
            return PREINTERMEDIATE;
        }else if(points < getPoints(4)){
            return INTERMEDIATE;
        }else if(points < getPoints(5)){
            return UPPERINTERMEDIATE;
        }else if(points < getPoints(6)){
            return ADVANCED;
        }else{
            return MASTER;
        }
    }

    public static String getAllPoints(){
        File file = new File("src/main/resources/Points");
        String str = "";
        try(Scanner sc = new Scanner(file)){
            str = sc.nextLine();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return str;
    }
}
