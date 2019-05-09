package controller.administration;

import dao.GrammarTheoryRepo;
import dao.LessonRepo;
import model.GrammarTheory;
import model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/administration/grammar")
public class GrammarTheoryAdminController extends TaskAdminController {
    private final GrammarTheoryRepo GrammarRepo;

    @Autowired
    public GrammarTheoryAdminController(LessonRepo lesRep, GrammarTheoryRepo rep){
        super(lesRep);
        GrammarRepo = rep;
    }

    @Override
    @PostMapping("/add")
    public String add(@RequestParam("lessonName") long lessonId) {
        Lesson lesson;
        try {
            lesson = getLesson(lessonId);
        }catch(IllegalArgumentException e){
            return "Incorrect id";
        }

        GrammarTheory gr = new GrammarTheory(lesson.getMax() + 1);
        lesson.addTask(gr);
        GrammarRepo.save(gr);
        return "OK";
    }

    @Override
    @PostMapping("/delete")
    public String delete(@RequestParam("lessonName") long taskId){
        Optional<GrammarTheory> opt = GrammarRepo.findById(taskId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        GrammarRepo.delete(opt.get());
        return "OK";
    }
}