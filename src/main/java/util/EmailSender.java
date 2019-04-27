package util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static String sendThroughRemote(String sender, String password, String recipient, String code) {
        Properties properties = System.getProperties();

        String fileName = "target/classes/mailproperties.txt";
        File file = new File(fileName);

        try {
            properties.load(new FileInputStream(file));
            Session session = Session.getDefaultInstance(properties);
            System.out.println(properties.getProperty("mail.smtp.socketFactory.port"));
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("Activation Code");
            message.setText(code);

            Transport tr = session.getTransport();
            tr.connect(sender, password);
            tr.sendMessage(message, message.getAllRecipients());
        } catch (FileNotFoundException e) {
            return "Fail. Properties were not found";
        } catch (IOException e1) {
            return "Fail. IOException was thrown";
        } catch (MessagingException e2) {
            System.out.println(e2.getMessage());
            return "Fail. MessagingException was thrown";

        }
        return "OK";
    }
}