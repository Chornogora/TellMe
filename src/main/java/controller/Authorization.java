package controller;

import dao.UserDao;
import model.SimpleUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class Authorization {

    @PostMapping("/authorize")
    public String authorize(
            @RequestParam("login") String login,
            @RequestParam("password") String password
    ){
        UserDao dao = new UserDao();
        System.out.println(SimpleUser.class);
        SimpleUser su = dao.findByName(login);
        if(su == null){
            return "Invalid login";
        }
        if(su.isPasswordCorrect(password)){
            return util.JSONparser.toJSON(su);
        }else{
            return "Invalid password";
        }
    }

    @GetMapping("/getauth")
    public String getauthorize(
            @RequestParam("login") String login,
            @RequestParam("password") String password
    ){
        UserDao dao = new UserDao();
        System.out.println(SimpleUser.class);
        SimpleUser su = dao.findByName(login);
        if(su == null){
            return "Invalid login";
        }
        if(su.isPasswordCorrect(password)){
            return util.JSONparser.toJSON(su);
        }else{
            return "Invalid password";
        }
    }
}