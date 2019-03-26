package model;

public enum Levels {
    ELEMENTARY(0), BEGINNER(100), PREINTERMEDIATE(500), INTERMEDIATE(5000), UPPERINTERMEDIATE(15000), ADVANCED(30000), MASTER(50000);

    Levels(int i){

    }

    static Levels getLevelByPoints(int points){
        if(points < 100){
            return ELEMENTARY;
        }else if(points >= 100 && points < 500){
            return BEGINNER;
        }else if(points >= 500 && points < 5000){
            return PREINTERMEDIATE;
        }else if(points >= 5000 && points < 15000){
            return INTERMEDIATE;
        }else if(points >= 15000 && points < 30000){
            return UPPERINTERMEDIATE;
        }else if(points >= 30000 && points < 50000){
            return ADVANCED;
        }else{
            return MASTER;
        }
    }
}
