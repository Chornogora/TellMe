package controller;

import model.Levels;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/level")
public class LevelsController {

    @GetMapping("/get")
    public String getLevels(){
        model.Levels[] array = model.Levels.values();
        Map<String, Integer> map = new HashMap<>();
        for(model.Levels level : array){
            map.put(level.name(), level.getMinPoints());
        }
        return util.JSONparser.toJSON(map);
    }

    @GetMapping("/getByPoints")
    public String getLevelByPoints(@RequestParam("points") int points){
        Levels level = Levels.getLevelByPoints(points);
        return level.toString();
    }
}
