package controller.administration;

import dao.LessonRepo;
import dao.LexicalTheoryRepo;
import model.Lesson;
import model.LexicalTheory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administration/lexical")
public class LexicalTheoryAdminController extends TaskAdminController{

    private final LexicalTheoryRepo LexicalRepo;

    @Autowired
    public LexicalTheoryAdminController(LessonRepo lesRep, LexicalTheoryRepo rep){
        super(lesRep);
        LexicalRepo = rep;
    }

    @PostMapping("/add")
    public String addLexicalTheory(@RequestParam("lessonName") long lessonId, @RequestParam("name") String name){
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
}