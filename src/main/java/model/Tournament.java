package model;

import java.util.List;

public class Tournament {
    private SimpleUser player1;

    private SimpleUser player2;

    private int score1;

    private int score2;

    public Tournament(SimpleUser user1, SimpleUser user2){
        this.player1 = user1;
        this.player2 = user2;
        score1 = 0;
        score2 = 0;
    }

    public void incrementScore1(){
        score1++;
    }

    public void incrementScore2(){
        score2++;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }
}
