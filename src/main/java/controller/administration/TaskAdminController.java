package controller.administration;

import dao.LessonRepo;
import model.Lesson;

import java.util.Optional;

public abstract class TaskAdminController {

    protected final LessonRepo lessonRepo;

    public TaskAdminController(LessonRepo rp){
        lessonRepo = rp;
    }

    public Lesson getLesson(long lessonId){
        Optional<Lesson> opt = lessonRepo.findById(lessonId);
        if(!opt.isPresent()){
            throw new IllegalArgumentException();
        }
        return opt.get();
    }

}
