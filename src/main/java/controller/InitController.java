package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@RestController
public class InitController {

    @GetMapping("/init")
    public String init(){
        System.out.println("Waked up");
        return "OK";
    }

    @GetMapping("/address")
    public String getAddress(){
        try {
            Properties props = new Properties();
            props.load(new FileReader("target/classes/serverProps.properties"));
            return props.getProperty("host");
        }catch(IOException e){
            e.printStackTrace();
        }
        return "Server Error";
    }
}
