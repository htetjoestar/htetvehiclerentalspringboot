package com.htetvehiclerental.htetvehiclerental.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.htetvehiclerental.htetvehiclerental.entity.Customer;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(Customer user, String token) {
        String url = "http://localhost:3000/verify?token=" + token;
        String subject = "Email Verification - Htet Vehicle Rental";
        String cid = "logoImage";

        String htmlContent = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<div style='text-align: center;'>" +
                "<img src='cid:" + cid + "' alt='Company Logo' style='width: 150px; margin-bottom: 20px;'/>" +
                "<h2>Welcome to Htet Vehicle Rental!</h2>" +
                "<p>Dear " + user.getCust_first_name() + " " + user.getCust_last_name() + ",</p>" +
                "<p>Thank you for registering with us. Please verify your email address by clicking the button below:</p>" +
                "<p><a href='" + url + "' style='display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;'>Verify Email</a></p>" +
                "<p>If you didn’t sign up, you can safely ignore this message.</p>" +
                "<p>Best regards,<br/>The Htet Vehicle Rental Team</p>" +
                "</div></body></html>";

        sendHtmlEmail(user.getCust_email(), subject, htmlContent, cid);
    }

    public void sendPasswordResetEmail(Customer user, String token) {
        String url = "http://localhost:3000/reset-password?token=" + token;
        String subject = "Password Reset - Htet Vehicle Rental";
        String cid = "logoImage";

        String htmlContent = "<html><body style='font-family: Arial, sans-serif;'>" +
                "<div style='text-align: center;'>" +
                "<img src='cid:" + cid + "' alt='Company Logo' style='width: 150px; margin-bottom: 20px;'/>" +
                "<h2>Password Reset Request</h2>" +
                "<p>Dear " + user.getCust_first_name() + " " + user.getCust_last_name() + ",</p>" +
                "<p>We received a request to reset your password. Click the button below to proceed:</p>" +
                "<p><a href='" + url + "' style='display: inline-block; padding: 10px 20px; background-color: #f44336; color: white; text-decoration: none; border-radius: 5px;'>Reset Password</a></p>" +
                "<p>If you didn’t request this, please ignore this message or contact support.</p>" +
                "<p>Best regards,<br/>The Htet Vehicle Rental Team</p>" +
                "</div></body></html>";

        sendHtmlEmail(user.getCust_email(), subject, htmlContent, cid);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent, String cid) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); 
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            ClassPathResource image = new ClassPathResource("/website_logo_sm.png"); 
            helper.addInline(cid, image);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Optional: log or rethrow
        }
    }
}