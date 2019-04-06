package model;

import java.util.List;

public class Tournament {
    private SimpleUser player1;

    private SimpleUser player2;

    private List<Test> questions;

    private int score1;

    private int score2;

    public Tournament(SimpleUser user1, SimpleUser user2){
        this.player1 = user1;
        this.player2 = user2;
        score1 = 0;
        score2 = 0;
    }

    public Tournament(SimpleUser user1, SimpleUser user2, List<Test> tests){
        this(user1, user2);
        this.questions = tests;
    }
}
