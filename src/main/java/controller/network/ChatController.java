package controller.network;

import controller.ElementGetter;
import dao.ChatRepo;
import dao.MessageRepo;
import dao.SimpleUserRepo;
import model.Chat;
import model.Message;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController implements ElementGetter {

    private final ChatRepo chatRepo;
    private final SimpleUserRepo userRepo;
    private final MessageRepo messageRepo;

    @Autowired
    public ChatController(ChatRepo repo, SimpleUserRepo suRepo, MessageRepo mRepo){
        chatRepo = repo;
        userRepo = suRepo;
        messageRepo = mRepo;
    }

    @Override
    @GetMapping("/get")
    public String get(@RequestParam("id") long chatId){
        Optional<Chat> opt = chatRepo.findById(chatId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        Chat chat = opt.get();
        return util.JSONparser.toJSON(chat);
    }

    @Override
    @GetMapping("/getAll")
    public String getAll(){
        Iterable<Chat> set = chatRepo.findAll();
        return util.JSONparser.toJSON(set);
    }

    @GetMapping("/messages/getAll")
    public String getMessages(@RequestParam("chatId") long chatId){
        Optional<Chat> opt = chatRepo.findById(chatId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        Chat chat = opt.get();
        List<Message> messages = chat.getMessages();
        return util.JSONparser.MessagetoJSON(messages);
    }

    @PostMapping("/create")
    public String createChat(@RequestParam("userId") long userId,
                             @RequestParam("theme") String theme){
        if(theme.length() == 0){
            return "Theme is absent";
        }
        Optional<SimpleUser> opt = userRepo.findById(userId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        SimpleUser user = opt.get();
        Chat chat = new Chat(user, theme);
        chatRepo.save(chat);
        return util.JSONparser.toJSON(chat);
    }

    @PostMapping("/open")
    public String openChat(@RequestParam("chatId") long chatId){
        Optional<Chat> opt = chatRepo.findById(chatId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        int port = SocketDispatcher.getChatPort(chatId);
        if(port != -1){
            return String.valueOf(port);
        }
        return String.valueOf(SocketDispatcher.createChatSocket(this, chatId));
    }

    public String addMessage(long chatId, long userId, String text){
        Optional<Chat> opt = chatRepo.findById(chatId);
        if(!opt.isPresent()){
            return "Invalid chat id";
        }

        Optional<SimpleUser> opt2 = userRepo.findById(userId);
        if(!opt2.isPresent()){
            return "Invalid user id";
        }

        SimpleUser user = opt2.get();
        Chat chat = opt.get();

        Message message = new Message(chat, user, text);
        messageRepo.save(message);
        return util.JSONparser.MessagetoJSON(message);
    }
}