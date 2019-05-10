package controller.personal;

import controller.AbstractAuthorization;
import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return authorize(user, password);
    }
}