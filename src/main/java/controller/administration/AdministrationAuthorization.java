package controller.administration;

import controller.AbstractAuthorization;
import dao.SimpleAdministratorRepo;
import model.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administration/authorization")
public class AdministrationAuthorization extends AbstractAuthorization {

    private final SimpleAdministratorRepo simpleAdministratorRepo;

    @Autowired
    public AdministrationAuthorization(SimpleAdministratorRepo repo){
        simpleAdministratorRepo = repo;
    }

    @PostMapping("/authorize")
    public String authorize(@RequestParam("login") String login, @RequestParam("password") String password){
        Administrator admin = simpleAdministratorRepo.findByLogin(login);
        return authorize(admin, password);
    }
}