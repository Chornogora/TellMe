package controller;

import dao.LessonRepo;
import model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/lesson")
public class LessonController implements ElementGetter{

    private final LessonRepo lRepo;

    @Autowired
    public LessonController(LessonRepo repo){
        lRepo = repo;
    }

    @Override
    @GetMapping("/get")
    public String get(@RequestParam("id") long lessonId){
        Optional<Lesson> opt = lRepo.findById(lessonId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        Lesson lesson = opt.get();
        return util.JSONparser.toJSON(lesson);
    }

    @Override
    @GetMapping("/getAll")
    public String getAll(){
        Iterable<Lesson> set = lRepo.findAll();
        return util.JSONparser.toJSON(set);
    }
}