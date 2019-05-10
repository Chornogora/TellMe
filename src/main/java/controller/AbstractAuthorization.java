package controller;

import model.SimpleUser;
import model.User;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractAuthorization {

   protected String authorize(User user, String password){

        if(user == null){
            return "Invalid login";
        }

        if(user.isPasswordCorrect(password)){
            return util.JSONparser.toJSON(user);
        }else{
            return "Invalid password";
        }
    }

}
