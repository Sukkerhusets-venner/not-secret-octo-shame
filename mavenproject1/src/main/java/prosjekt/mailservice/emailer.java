package prosjekt.mailservice;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class emailer {

    private String sender = "chil@noreply.com"; //Vår email !
    private String recipient;
    private String host;

    public emailer(String to, String h) {
        recipient = to;
        this.host = h;
    }
    
    public void setMailer(String from){
        this.sender = from;
    }
    
    public String getMailer(){
        return sender;
    }
    
    public void setHost(String h){
        this.host = h;
    }
    public String getHost(){
        return host;
    } 

    public boolean sendMessage(String subject, String message) {
      
        boolean success = false;
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage mmessage = new MimeMessage(session);
            // Set From: header field of the header.
            mmessage.setFrom(new InternetAddress(sender));
            // Set To: header field of the header.
            mmessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: header field
            mmessage.setSubject(subject);

            // Now set the actual message
            mmessage.setText(message);

            // Send message
            Transport.send(mmessage);
            success = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return success;
    }
}