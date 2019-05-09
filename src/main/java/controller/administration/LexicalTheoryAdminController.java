package controller.administration;

import dao.LessonRepo;
import dao.LexicalTheoryRepo;
import model.Lesson;
import model.LexicalTheory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/administration/lexical")
public class LexicalTheoryAdminController extends TaskAdminController{

    private final LexicalTheoryRepo LexicalRepo;

    @Autowired
    public LexicalTheoryAdminController(LessonRepo lesRep, LexicalTheoryRepo rep){
        super(lesRep);
        LexicalRepo = rep;
    }

    @Override
    @PostMapping("/add")
    public String add(@RequestParam("lessonName") long lessonId){
        Lesson lesson;
        try {
            lesson = getLesson(lessonId);
        }catch(IllegalArgumentException e){
            return "Incorrect id";
        }

        LexicalTheory lt = new LexicalTheory(lesson.getMax()+1);
        lesson.addTask(lt);
        LexicalRepo.save(lt);
        return "OK";
    }

    @Override
    @PostMapping("/delete")
    public String delete(@RequestParam("lessonName") long taskId){
        Optional<LexicalTheory> opt = LexicalRepo.findById(taskId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        LexicalRepo.delete(opt.get());
        return "OK";
    }
}