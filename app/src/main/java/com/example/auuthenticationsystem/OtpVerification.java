package com.example.auuthenticationsystem;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OtpVerification {

    // Your email settings
    private static final String EMAIL_USERNAME = "gachago.dev@gmail.com"; // Replace with your email
    private static final String EMAIL_PASSWORD = "Gachago@1998.me"; // Replace with your email password

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Method to generate a random OTP
    public static String generateOTP(int otpLength) {
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder(otpLength);
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < otpLength; i++) {
            int index = secureRandom.nextInt(numbers.length());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }

    // Method to send OTP via email
    /*public static void sendOTPEmail(Context context, String email, String otp) {
        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.example.com"); // Replace with your SMTP host
        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port

        // Create session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME)); // Sender's email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Recipient's email
            message.setSubject("Your OTP"); // Email subject
            message.setText("Your OTP is: " + otp); // Email content

            // Send message
            Transport.send(message);
            Toast.makeText(context, "OTP email sent successfully to " + email, Toast.LENGTH_SHORT).show();
            System.out.println("OTP email sent successfully to " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to send OTP email to " + email, Toast.LENGTH_SHORT).show();
            System.out.println("Failed to send OTP email to " + email);
        }
    }

    // Method to send OTP to the current user's email
    public static void sendOTP(Context context) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null) {
                String otp = generateOTP(6); // Generate a 6-digit OTP
                sendOTPEmail(context, email, otp);
            } else {
                Toast.makeText(context, "User's email is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
    */

    // Method to verify OTP
    public static boolean verifyOTP(String userInputOTP, String generatedOTP) {
        return userInputOTP.equals(generatedOTP);
    }
}
