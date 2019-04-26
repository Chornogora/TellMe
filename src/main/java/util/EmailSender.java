package util;

import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static String sendThroughRemote(String sender, String password, String recipient, String code){
        Properties properties = System.getProperties();

        String fileName = "target/classes/mailproperties.txt";
        File file = new File(fileName);
        String way = file.getAbsolutePath();

        /*ClassLoader classLoader = EmailSender.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());*/

        try {
            properties.load(new FileInputStream(file));
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("Activation Code");
            message.setText(code);

            Transport tr = session.getTransport();
            tr.connect(sender, password);
            tr.sendMessage(message, message.getAllRecipients());
        }catch(FileNotFoundException e){
            return "Fail. Properties were not found";
        }catch(IOException e1){
            return "Fail. IOException was thrown";
        }catch(MessagingException e2){
            System.out.println(e2.getMessage());
            return "Fail. MessagingException was thrown";

        }
        return "OK";
    }
}