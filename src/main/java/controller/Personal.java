package controller;

import dao.SimpleUserRepo;
import model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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

    @PostMapping("/setAvatar")
    public String setAvatar(@RequestParam("id") long id, @RequestParam("avatar") MultipartFile image){
        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            return "Incorrect id";
        }
        SimpleUser user = opt.get();
        try {
            Files.copy(image.getInputStream(), Paths.get("src/main/resources/images/" + user.getId() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            try {
                File file = new File("src/main/resources/images/" + user.getLogin() + ".jpg");
                String s = file.getAbsolutePath();
                file.createNewFile();
                Files.copy(image.getInputStream(), Paths.get("src/main/resources/images/" + user.getId() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e2){
                return "Error";
            }
        }

        return "OK";
    }

    @GetMapping("/getAvatar")
    public byte[] getAvatar(@RequestParam("id") long id) throws IOException {
        Optional<SimpleUser> opt = simpleUserRepo.findById(id);
        if(!opt.isPresent()){
            throw new IllegalArgumentException();
        }
        SimpleUser user = opt.get();

        File file = new File("src/main/resources/images/" + user.getId() + ".jpg");
        String temp = file.getAbsolutePath();
        System.out.println(temp);
        BufferedImage bf;
        try {
            bf = ImageIO.read(file);
        }catch(IOException e){
            bf = ImageIO.read(new File("src/main/resources/images/anonymous.jpg"));
        }

        WritableRaster raster = bf .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
        return data.getData();
    }
}
