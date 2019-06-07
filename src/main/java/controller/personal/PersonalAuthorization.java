package controller.personal;

import controller.AbstractAuthorization;
import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authorization")
public class PersonalAuthorization extends AbstractAuthorization {

    private final SimpleUserRepo simpleUserRepo;

    @Autowired
    public PersonalAuthorization(SimpleUserRepo simpleUserRepo) {
        this.simpleUserRepo = simpleUserRepo;
    }

    @PostMapping("/authorize")
    public String authorize(@RequestParam("login") String login, @RequestParam("password") String password){
        SimpleUser user = simpleUserRepo.findByLogin(login);
        if(user == null){
            return "Invalid login";
        }
        user.setLevel();
        return super.authorize(user, password);
    }

    @GetMapping("/get")
    public String getUser(@RequestParam("id") long id){
        SimpleUser user;
        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        user = opt.get();
        user.setLevel();
        return util.JSONparser.toJSON(user);
    }
}