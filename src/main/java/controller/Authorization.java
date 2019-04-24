package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class Authorization {

    private final SimpleUserRepo simpleUserRepo;

    @Autowired
    public Authorization(SimpleUserRepo simpleUserRepo) {
        this.simpleUserRepo = simpleUserRepo;
    }

    @GetMapping("/getauth")
    public String getauthorize(@RequestParam("login") String login, @RequestParam("password") String password){
        SimpleUser user = simpleUserRepo.findByLogin(login);

        if(user == null){
            return "Invalid login";
        }

        if(user.isPasswordCorrect(password)){
            return util.JSONparser.toJSON(user);
        }else{
            return "Invalid password";
        }
    }

    @GetMapping("/test")
    public String test(){
        return "Hello, World!";
    }
}