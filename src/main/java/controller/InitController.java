package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

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

    @GetMapping("/")
    private String getRedirect(){
        StringBuilder result = new StringBuilder();
        try(Scanner sc = new Scanner(new File("target/classes/static/redirect.html"))) {
            while(sc.hasNextLine()){
                result.append(sc.nextLine()).append('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
