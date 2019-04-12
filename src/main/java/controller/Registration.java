package controller;

import dao.UserDao;
import model.SimpleUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/registration")
public class Registration {

    @PostMapping("/register")
    public String register(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("birthday") Date birthday
    ){
        UserDao dao = new UserDao();
        SimpleUser su;
        if(birthday == null){
            su = new SimpleUser(login, password, email);
        }else{
            su = new SimpleUser(login, password, email, birthday);
        }
        dao.save(su);
        return util.JSONparser.toJSON(su);
    }

}
