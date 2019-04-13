package dao;

import model.SimpleUser;

public class Main {
    public static void main(String[] args) {

        String login="basta";
        String password = "12345";

        String user = getUser(login, password);
        System.out.println(user);
    }

    private static String getUser(String login, String password){
        UserDao dao = new UserDao();
        SimpleUser su = dao.findByName(login);
        if(su == null){
            return "Invalid login";
        }
        if(su.isPasswordCorrect(password)){
            return util.JSONparser.toJSON(su);
        }else{
            return "Invalid password";
        }
    }

}
