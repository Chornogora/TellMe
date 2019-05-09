package controller.administration;

import dao.LessonRepo;
import model.Lesson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/administration/grammar")
public abstract class TaskAdminController {

    private final LessonRepo lessonRepo;

    public TaskAdminController(LessonRepo rp){
        lessonRepo = rp;
    }

    Lesson getLesson(long lessonId){
        Optional<Lesson> opt = lessonRepo.findById(lessonId);
        if(!opt.isPresent()){
            throw new IllegalArgumentException();
        }
        return opt.get();
    }

    public abstract String add(long lessonId);

    public abstract String delete(long taskId);

}