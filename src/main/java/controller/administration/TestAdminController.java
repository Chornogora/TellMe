package controller.administration;

import dao.LessonRepo;
import dao.TestRepo;
import dao.VariantRepo;
import model.Lesson;
import model.Test;
import model.Variant;
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
    private final VariantRepo variantRepo;

    @Autowired
    public TestAdminController(LessonRepo lesRep, TestRepo rep, VariantRepo vRep){
        super(lesRep);
        testRepo = rep;
        variantRepo = vRep;
    }

    @Override
    @PostMapping("/delete")
    public String delete(@RequestParam("lessonId") long taskId) {
        Optional<Test> opt = testRepo.findById(taskId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        testRepo.delete(opt.get());
        return "OK";
    }

    @PostMapping("/add")
    public String add(@RequestParam("lessonId") long lessonId,
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
            test = new Test(lesson, lesson.getMax()+1, picture, text, type);
        }catch(IllegalArgumentException e){
            return "Incorrect type";
        }

        lesson.addTask(test);
        testRepo.save(test);
        lessonRepo.save(lesson);
        return util.JSONparser.toJSON(test);
    }

    @PostMapping("/addVariant")
    public String addVariant(@RequestParam("taskId") long grTheoryId,
                             @RequestParam("number") int num,
                             @RequestParam("rightVariant") String right,
                             @RequestParam("text") String text){
        Optional<Test> opt = testRepo.findById(grTheoryId);
        if(!opt.isPresent()){
            return "Incorrect id";
        }

        Test test = opt.get();
        Variant variant = new Variant(num, right, text);
        test.addVariant(variant);
        variantRepo.save(variant);

        return "OK";
    }
}