package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/levels")
public class Levels {

    @GetMapping("/get")
    public String getLevels(){
        model.Levels[] array = model.Levels.values();
        Map<String, Integer> map = new HashMap<>();
        for(model.Levels level : array){
            map.put(level.name(), level.getMinPoints());
        }
        return util.JSONparser.toJSON(map);
    }
}
