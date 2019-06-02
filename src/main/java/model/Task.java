package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="tasks")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private long id;

    @Column(name="task_number")
    private int number;

    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public Task(){

    }

    public Task(Lesson lesson, int number){
        this.lesson = lesson;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        Task task = lesson.getTaskByNumber(number);
        if(task != null){
            task.setNumber(this.number);
        }
        this.number = number;
    }

}
