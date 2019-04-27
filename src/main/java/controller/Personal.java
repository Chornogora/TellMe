package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public String setAvatar(HttpServletRequest request, @RequestParam("id") long id, @RequestParam("avatar") MultipartFile image){
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
            Path path = Paths.get("" + user.getId() + extension);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File(STATIC_IMAGES_ROOT + user.getId() + extension);
                boolean isCreated = file.createNewFile();
                if(!isCreated){
                    throw new IOException();
                }
                Files.copy(image.getInputStream(), Paths.get(STATIC_IMAGES_ROOT + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                return "Error";
            }
        }

        try {
            Files.copy(image.getInputStream(), Paths.get( + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File("target/static/" + DYNAMIC_IMAGES_ROOT  + user.getId() + extension);
                boolean isCreated = file.createNewFile();
                if(!isCreated){
                    throw new IOException();
                }
                Files.copy(image.getInputStream(), Paths.get("target/static/" + DYNAMIC_IMAGES_ROOT  + user.getId() + extension), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                return "Error";
            }
        }

        user.setAvatar(getImagePath(request, user.getId() + extension));
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

    private String getImagePath(HttpServletRequest request, String uniqueName){
        String address = request.getRequestURL().toString();
        int port = request.getServerPort();

        String hostAndPort = address.substring(0, address.indexOf(String.valueOf(port)) + String.valueOf(port).length()+1);
        return hostAndPort + DYNAMIC_IMAGES_ROOT + uniqueName;
    }
}
