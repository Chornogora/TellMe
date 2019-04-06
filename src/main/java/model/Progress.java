package model;

import javax.persistence.*;

@Entity
@Table(name="progresses")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="progress_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SimpleUser user;

    @Column(name = "progress_taskpassednumber")
    private int taskPassedNumber;

    @Column(name = "progress_isdone")
    private boolean isDone;

    public Progress(Lesson lesson, SimpleUser user){
        this.lesson = lesson;
        this.user = user;
        this.taskPassedNumber = 0;
        this.isDone = false;
    }

    public int getTaskPassedNumber() {
        return taskPassedNumber;
    }

    public void setTaskPassedNumber(int taskPassedNumber) {
        this.taskPassedNumber = taskPassedNumber;
        if(taskPassedNumber == lesson.getTasks().size()){
            isDone = true;
        }
    }
}
