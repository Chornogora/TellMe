package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @GetMapping("/init")
    public String init(){
        System.out.println("Waked up");
        return "OK";
    }
}
