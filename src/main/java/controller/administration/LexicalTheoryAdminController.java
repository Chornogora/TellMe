package controller.administration;

import dao.LessonRepo;
import dao.LexicalTheoryRepo;
import dao.WordRepo;
import model.Lesson;
import model.LexicalTheory;
import model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/administration/lexical")
public class LexicalTheoryAdminController extends TaskAdminController{

    private final LexicalTheoryRepo LexicalRepo;
    private final WordRepo wordRepo;

    @Autowired
    public LexicalTheoryAdminController(LessonRepo lesRep, LexicalTheoryRepo rep, WordRepo wRepo){
        super(lesRep);
        LexicalRepo = rep;
        wordRepo = wRepo;
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

    @PostMapping("/addWord")
    public String addWord(@RequestParam("lessonName") long lexTheoryId,
                          @RequestParam("wordName") String name,
                          @RequestParam("description") String desc,
                          @RequestParam("translation") String trans,
                          @RequestParam("picture") String picture){
        Optional<LexicalTheory> opt = LexicalRepo.findById(lexTheoryId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        LexicalTheory theory = opt.get();
        Word word = new Word(name, desc, trans, picture);
        theory.addWord(word);
        wordRepo.save(word);
        return "OK";
    }

}