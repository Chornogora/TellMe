package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private long id;

    @Column(name="lesson_name")
    private String name;

    @Column(name="lesson_points")
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_level")
    private Levels level;

    @Column(name = "lesson_opened")
    private boolean isOpened;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<Progress> progresses;


    public Lesson(){

    }

    public Lesson(String name, int points, Levels lvl){
        this.name = name;
        this.points = points;
        this.level = lvl;
        this.isOpened = false;
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

    public void open(){
        this.isOpened = true;
    }

    public void close(){
        this.isOpened = false;
    }

    public boolean isOpened() {
        return isOpened;
    }
}