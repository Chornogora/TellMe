package controller;

import dao.LessonRepo;
import dao.ProgressRepo;
import dao.SimpleUserRepo;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/progress")
public class ProgressController implements ElementGetter{

    private final ProgressRepo prRepo;
    private final SimpleUserRepo userRepo;
    private final LessonRepo lRepo;

    @Autowired
    public ProgressController(ProgressRepo r, SimpleUserRepo r2, LessonRepo r3){
        prRepo = r;
        userRepo = r2;
        lRepo = r3;
    }

    @GetMapping("/get")
    public String get(@RequestParam(name="userId") long userId){

        //<TODO Filter>
        //...
        //</TODO Filter>

        Optional<SimpleUser> opt = userRepo.findById(userId);
        if(!opt.isPresent()) {
            return "Invalid id";
        }

        List<Progress> progresses = prRepo.findByUser_id(userId);

        return util.JSONparser.ProgresstoJSON(progresses);


    }

    @GetMapping("/getAll")
    public String getAll(){

        Iterable<Progress> progresses = prRepo.findAll();

        return util.JSONparser.ProgresstoJSON(progresses);


    }

    @PostMapping("/passTheory")
    public String passTask(@RequestParam("progressId") long id){
        Optional<Progress> opt = prRepo.findById(id);
        if(!opt.isPresent()) {
            return "Invalid id";
        }
        Progress progress = opt.get();
        try {
            Task task = (progress.getLesson().getTaskByNumber(progress.getTaskPassedNumber()+1));
            task = (Test)task;
            return "Task is a Test";
        }catch (ClassCastException e){
            passTask(progress);
        }
        return  util.JSONparser.ProgresstoJSON(progress);
    }

    private void passTask(Progress progress){
        progress.setTaskPassedNumber(progress.getTaskPassedNumber() + 1);
        if (progress.getTaskPassedNumber()+1 == progress.getLesson().getTasks().size() && !progress.isDone()) {
            progress.setDone();
            progress.getUser().addPoints(progress.getLesson().getPoints());
            userRepo.save(progress.getUser());
        }
        prRepo.save(progress);
    }

    @PostMapping("/passTest")
    public String passTest(@RequestParam("progressId") long id,
                           @RequestParam("answer") String answer){
        Optional<Progress> opt = prRepo.findById(id);
        if(!opt.isPresent()) {
            return "Invalid id";
        }
        Progress progress = opt.get();

        Test test;
        try {
            test = (Test) progress.getLesson().getTaskByNumber(progress.getTaskPassedNumber()+1);
        }catch(ClassCastException e){
            return "Task is not a Test";
        }

        if(test.isPassed(answer)){
            passTask(progress);
            return util.JSONparser.ProgresstoJSON(progress);
        }

        return "Failed";
    }
}