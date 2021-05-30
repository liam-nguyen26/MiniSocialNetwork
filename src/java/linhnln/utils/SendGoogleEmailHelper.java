/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.utils;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Administrator
 */
public class SendGoogleEmailHelper implements Serializable {
    private static final String MY_ACCOUNT_EMAIL = "XXXX";
    private static final String MY_ACCOUNT_PASSWORD = "XXXX";
    
    public static boolean sendMail(String recepient, String otp) throws MessagingException {
        Properties properties = new Properties();
        //key and value
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MY_ACCOUNT_EMAIL, MY_ACCOUNT_PASSWORD);
            }
        });
        Message message = prepareMess(session, MY_ACCOUNT_EMAIL, recepient, otp);
        
        if (message != null) {
            Transport.send(message);
            return true;
        }
        return false;
    }

    private static Message prepareMess(Session session, String myAccountEmail, String recepient, String otp) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("Confirmation code for social network");
        message.setText("Your OTP is: " + otp);
        return message;

    }

}
