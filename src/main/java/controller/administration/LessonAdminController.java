package controller.administration;

import dao.LessonRepo;
import model.Lesson;
import model.Levels;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administration/lesson")
public class LessonAdminController {

    private LessonRepo lessonRepo;

    public LessonAdminController(LessonRepo repo){
        this.lessonRepo = repo;
    }

    @PostMapping("/add")
    public String addLesson(@RequestParam("name") String name,
                         @RequestParam("points") int points,
                         @RequestParam("level") String level){

        Lesson existLesson = lessonRepo.findByName(name);
        if(existLesson != null){
            return "Lesson exists";
        }

        Lesson lesson;
        try {
            lesson = new Lesson(name, points, Levels.valueOf(level));
        }catch(IllegalArgumentException e){
            return "Illegal arguments";
        }

        lessonRepo.save(lesson);
        return "OK";
    }

    @PostMapping("/delete")
    public synchronized String deleteLesson(@RequestParam("name") String name){
        Lesson existLesson = lessonRepo.findByName(name);
        String status = getStatus(existLesson);
        if(!status.equals("OK")){
            return status;
        }

        if(existLesson.isOpened()){
            return "Opened";
        }

        lessonRepo.delete(existLesson);
        return "OK";
    }

    @PostMapping("/update")
    public String updateLesson(@RequestParam("name") String name,
                               @RequestParam("points") int points,
                               @RequestParam("level") String level){
        Lesson existLesson = lessonRepo.findByName(name);
        String status = getStatus(existLesson);
        if(!status.equals("OK")){
            return status;
        }

        existLesson.setLevel(Levels.valueOf(level));
        existLesson.setPoints(points);
        lessonRepo.save(existLesson);
        return "OK";
    }

    @PostMapping("/open")
    private String openLesson(@RequestParam("name") String name){
        Lesson existLesson = lessonRepo.findByName(name);
        String status = getStatus(existLesson);
        if(!status.equals("OK")){
            return status;
        }

        existLesson.open();
        lessonRepo.save(existLesson);
        return("OK");
    }

    @PostMapping("/close")
    private String closeLesson(@RequestParam("name") String name){
        Lesson existLesson = lessonRepo.findByName(name);
        String status = getStatus(existLesson);
        if(!status.equals("OK")){
            return status;
        }

        existLesson.close();
        lessonRepo.save(existLesson);
        return("OK");
    }

    private String getStatus(Lesson existLesson){
        if(existLesson == null){
            return "Not exists";
        }

        if(existLesson.isOpened()){
            return "Opened";
        }
        return "OK";
    }

}