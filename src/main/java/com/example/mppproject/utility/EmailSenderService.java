package com.example.mppproject.utility;

import com.google.auto.value.extension.serializable.SerializableAutoValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    public void sendEmail(String emailTo, String emailSubject, String emailBody ){

        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setText(emailBody);
        simpleMailMessage.setSubject(emailSubject);

        if(simpleMailMessage != null) {
            mailSender.send(simpleMailMessage);
            System.out.println("Message sent s>>>>>>>>...");
        }
    }
}
