package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@RequestMapping("/personal")
public class Personal {

    private final SimpleUserRepo simpleUserRepo;

    @Autowired
    public Personal(SimpleUserRepo simpleUserRepo) {
        this.simpleUserRepo = simpleUserRepo;
    }

    @PostMapping("setAvatar")
    public String setAvatar(@RequestParam("id") long id, @RequestParam("avatar") MultipartFile image){
        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            return "Incorrect id";
        }
        SimpleUser user = opt.get();
        try {
            Files.copy(image.getInputStream(), Paths.get("src/main/resources/images/" + user.getLogin() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File("src/main/resources/images/" + user.getLogin() + ".jpg");
                String s = file.getAbsolutePath();
                file.createNewFile();
                Files.copy(image.getInputStream(), Paths.get("src/main/resources/images/" + user.getLogin() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                return "Error";
            }

        }

        try{
            Files.copy(image.getInputStream(), Paths.get("target/classes/images/" + user.getLogin() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File("target/classes/images/" + user.getLogin() + ".jpg");
                file.createNewFile();
                Files.copy(image.getInputStream(), Paths.get("target/classes/images/" + user.getLogin() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                return "Error";
            }
        }

        return "OK";
    }
}
