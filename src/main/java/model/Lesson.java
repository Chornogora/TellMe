package model;

import java.util.List;

public class Lesson {
    private String name;

    private int points;

    private List<Task> tasks;

    private Levels level;

    public Lesson(String name, int points, Levels lvl){
        this.name = name;
        this.points = points;
        this.level = lvl;
    }

    public Lesson(String name, int points, Levels lvl, List<Task> lst){
        this(name, points, lvl);
        this.tasks = lst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }
}