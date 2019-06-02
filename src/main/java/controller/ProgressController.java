package controller;

import dao.LessonRepo;
import dao.ProgressRepo;
import dao.SimpleUserRepo;
import model.Lesson;
import model.Progress;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
