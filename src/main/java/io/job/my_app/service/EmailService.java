package io.job.my_app.service;


import io.job.my_app.Entity.Security.EmailVerification;
import io.job.my_app.repos.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    // send email
    public boolean sendEmail(String msg1, String subject1, String to1) {
        boolean f = false;
        System.out.println("Preparing to send msg");
        String from1 = "springboot413@gmail.com";
        // get the system properties
        Properties properties = System.getProperties();
        System.out.println(properties);

        // setting imp infromation to properties object

        // host set
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String uname = "springboot413@gmail.com";
        String pass = "himclckbsfvzcxlk";

        // step1 : to get the session object

        Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(uname, pass);
            }

        });
        session.setDebug(true);

        // step 2: compose msg
        MimeMessage mm = new MimeMessage(session);

        try {
            // from email
            mm.setFrom(new InternetAddress(from1));

            // adding recipient
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to1));

            // subject
            mm.setSubject(subject1);

            // adding text to msg
            mm.setText(msg1);

            // send
            // step3:send the msg using transport class
            Transport.send(mm);

            System.out.println("sent succesfully");
            f = true;
        } catch (MessagingException e) {

            e.printStackTrace();
        }
        return f;

    }

    public void saveEmail(String email, String otp, LocalDateTime localDateTime) {
        if (email != null) {
            EmailVerification emailVerification = emailVerificationRepository.findByEmail(email);
            if (emailVerification == null) {
                // If the email doesn't exist, create a new entity
                emailVerification = new EmailVerification();
                emailVerification.setEmail(email);
            }

            // Update or set other attributes
            emailVerification.setOtp(otp);
            emailVerification.setStatus("Not verified");
            emailVerification.setCreationTime(localDateTime);

            this.emailVerificationRepository.save(emailVerification); // Save the entity (either new or updated)
        }
    }
}
