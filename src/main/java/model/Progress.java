package model;

public class Progress {

    private Lesson lesson;

    private SimpleUser user;

    private int taskPassedNumber;

    private boolean isDone;

    public Progress(Lesson lesson, SimpleUser user){
        this.lesson = lesson;
        this.user = user;
        this.taskPassedNumber = 0;
        this.isDone = false;
    }
}
