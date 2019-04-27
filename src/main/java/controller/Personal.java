package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/personal")
public class Personal{

    //Директория в папке 'target/static/', в которой хранятся загруженные изображения
    private static final String DYNAMIC_IMAGES_ROOT = "images/";
    //Директория, в которой хранятся все изображения. При запуске сервера они загружаются в DYNAMIC_IMAGES_ROOT
    private static final String STATIC_IMAGES_ROOT = "src/main/resources/static/images/";

    private final SimpleUserRepo simpleUserRepo;

    @Autowired
    public Personal(SimpleUserRepo simpleUserRepo) {
        this.simpleUserRepo = simpleUserRepo;
    }

    @PostMapping("/setAvatar")
    public String setAvatar(@RequestParam("id") long id, @RequestParam("avatar") MultipartFile image){
        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            return "Incorrect id";
        }
        SimpleUser user = opt.get();
        String imageName = image.getOriginalFilename();
        if(imageName == null){
            throw new IllegalArgumentException("No image");
        }
        int place = imageName.lastIndexOf('.');
        String extension = imageName.substring(place).toLowerCase();

        try {
            Path path = Paths.get(STATIC_IMAGES_ROOT + user.getId() + extension);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File(STATIC_IMAGES_ROOT + user.getId() + extension);
                boolean isCreated = file.createNewFile();
                if(!isCreated){
                    throw new IOException("Cannot create file in /src");
                }
                Files.copy(image.getInputStream(), Paths.get(STATIC_IMAGES_ROOT + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                e2.printStackTrace();
                return "Error in /src";
            }
        }

        try {
            Files.copy(image.getInputStream(), Paths.get( "target/classes/static/" + DYNAMIC_IMAGES_ROOT + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File("target/classes/static/" + DYNAMIC_IMAGES_ROOT  + user.getId() + extension);
                boolean isCreated = file.createNewFile();
                if(!isCreated){
                    throw new IOException("Cannot create file in /target");
                }
                Files.copy(image.getInputStream(), Paths.get("target/classes/static/" + DYNAMIC_IMAGES_ROOT  + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                e2.printStackTrace();
                return "Error in /target";
            }
        }

        user.setAvatar(DYNAMIC_IMAGES_ROOT + user.getId() + extension);
        simpleUserRepo.save(user);
        return user.getAvatar();
    }

    @GetMapping("/getAvatar")
    public String getAvatar(@RequestParam("id") long id){

        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            throw new IllegalArgumentException("Invalid id");
        }
        SimpleUser user = opt.get();
        return user.getAvatar();
    }
}
