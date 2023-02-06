package main;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class MailSender {

    public static void sendCouponToEmail() {
        String username = "cuponheart@mail.ru";
        String password = "xjBkF6KhNtyDBPhvTH2B";


        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mail.ru");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("cuponheart@mail.ru"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("hatlerrr@mail.ru"));
            message.setSubject("Here is your new coupon! Enjoy =)");

            Multipart emailContent = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("You have a new coupon!");

            MimeBodyPart pngAttachment = new MimeBodyPart();
            pngAttachment.attachFile("C:\\Users\\Zigor\\Desktop\\projects\\GirlfriendCouponValidation\\src\\main\\images\\finalCoupon.png");

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pngAttachment);

            message.setContent(emailContent);

            Transport.send(message);
            System.out.println("Message sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
