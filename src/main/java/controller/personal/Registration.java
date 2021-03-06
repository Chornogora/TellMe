package controller.personal;

import dao.LessonRepo;
import dao.ProgressRepo;
import dao.SimpleUserRepo;
import model.Lesson;
import model.Progress;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

@RestController
@RequestMapping("/registration")
public class Registration {

    private final SimpleUserRepo simpleUserRepo;
    private final ProgressRepo prRepo;
    private final LessonRepo lRepo;
    private HashMap<String, SimpleUser> codes;

    @Autowired
    public Registration(SimpleUserRepo simpleUserRepo, ProgressRepo pr, LessonRepo lr) {
        this.simpleUserRepo = simpleUserRepo;
        this.prRepo = pr;
        this.lRepo = lr;
        codes = new HashMap<>();
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday
    ) {
        Properties properties = util.Property.getInstance().getProperties("src/main/resources/mailBoxSecondConfig.properties");
        String sender = properties.getProperty("mailBox.Email");
        String pass = properties.getProperty("mailBox.Password");

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
            return "Invalid code";
        }
        simpleUserRepo.save(user);

        //<Create Progresses>

        Iterable<Lesson> lessons = lRepo.findAll();
        for(Lesson lesson : lessons){
            Progress progress = new Progress(lesson, user);
            prRepo.save(progress);
        }

        //</Create Progresses>

        return util.JSONparser.toJSON(user);
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("login") String login) {
        //TODO
        SimpleUser user = simpleUserRepo.findByLogin(login);
        return "null";
    }

}
