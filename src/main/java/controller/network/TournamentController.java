package controller.network;

import dao.LessonRepo;
import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private final SimpleUserRepo userRepo;

    public final LessonRepo lRepo;

    @Autowired
    public TournamentController(SimpleUserRepo suRepo, LessonRepo lr){
        userRepo = suRepo;
        lRepo = lr;
    }

    @PostMapping("open")
    public String openTournament(@RequestParam("id") long userId){
        Optional<SimpleUser> opt = userRepo.findById(userId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        SimpleUser user = opt.get();
        int port = SocketDispatcher.createTournamentSocket(this, user);

        if(port == -1){
            return "Cannot open Chat";
        }

        return String.valueOf(port);
    }

}