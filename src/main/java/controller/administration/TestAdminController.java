package controller.administration;

import dao.LessonRepo;
import dao.TestRepo;
import model.Lesson;
import model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/administration/test")
public class TestAdminController extends TaskAdminController{
    private final TestRepo testRepo;

    @Autowired
    public TestAdminController(LessonRepo lesRep, TestRepo rep){
        super(lesRep);
        testRepo = rep;
    }

    @Override
    @PostMapping("/delete")
    public String delete(@RequestParam("lessonName") long taskId) {
        Optional<Test> opt = testRepo.findById(taskId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        testRepo.delete(opt.get());
        return "OK";
    }

    @PostMapping("/add")
    public String add(@RequestParam("lessonName") long lessonId,
                      @RequestParam("picture") String picture,
                      @RequestParam("text") String text,
                      @RequestParam("type") String type){
        Lesson lesson;
        try {
            lesson = getLesson(lessonId);
        }catch(IllegalArgumentException e){
            return "Incorrect id";
        }

        Test test;
        try{
            test = new Test(lesson.getMax()+1, picture, text, type);
        }catch(IllegalArgumentException e){
            return "Incorrect type";
        }

        lesson.addTask(test);
        testRepo.save(test);
        return "OK";
    }

    @Override
    public String add(@RequestParam("lessonName") long lessonId) {
        return null;
    }
}