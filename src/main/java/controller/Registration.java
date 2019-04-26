package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/registration")
public class Registration {

    private final SimpleUserRepo simpleUserRepo;
    private HashMap<String, SimpleUser> codes;

    @Autowired
    public Registration(SimpleUserRepo simpleUserRepo) {
        this.simpleUserRepo = simpleUserRepo;
        codes = new HashMap<>();
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday
    ) {
        String[] mailBox = util.EmailSender.getBoxMailInformation();
        String sender = mailBox[0];
        String pass = mailBox[1];

        SimpleUser existsUser = simpleUserRepo.findByLogin(login);
        if (existsUser != null || codes.values().stream().anyMatch(user -> {
            String temp = user.getLogin();
            return temp.equals(login);
        })) {
            return "Login exists";
        }

        SimpleUser user = (birthday == null) ? new SimpleUser(login, password, email) : new SimpleUser(login, password, email, birthday);
        String code = util.CodeGenerator.generateCode();
        codes.put(code, user);
        util.EmailSender.sendThroughRemote(sender, pass, email, code);
        return "OK";
    }

    @PostMapping("/confirm")
    public String confirmCode(@RequestParam("code") String code) {
        SimpleUser user = codes.get(code);
        if (user == null) {
            return "null";
        }
        simpleUserRepo.save(user);
        return util.JSONparser.toJSON(user);
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("login") String login) {
        //TODO
        SimpleUser user = simpleUserRepo.findByLogin(login);
        return "null";
    }

}
