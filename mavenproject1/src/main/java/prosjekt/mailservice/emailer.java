package prosjekt.mailservice;



import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;


public class emailer {
    
    /**
     *
     * @param toEmail
     * @param toUsername
     * @param toPassword
     */
    public static void email (String toEmail, String toUsername, String toPassword){
        
        final String to = toEmail;
        final String fromUsername = "PassordBotCHIL@gmail.com";
        final String fromPassword = "CHIL1234";
        Properties properties = new Properties ();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUsername, fromPassword);
            }
          });
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(fromUsername));

         message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            message.setSubject("Ditt Passord for CHIL");
            message.setText("Hei " + toUsername
                + "\n\nHer er det nye passordet ditt til CHIL: " + toPassword);


         // Send message
         Transport.send(message);
      }catch (MessagingException mex) {
         mex.printStackTrace();
        }          
     }
    
    public static boolean validator (String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        return m.matches();
    }
  }