package com.otp.OTPEmailGenerator.service;
import com.otp.OTPEmailGenerator.model.MessageRes;
import com.otp.OTPEmailGenerator.model.OTPReq;
import com.otp.OTPEmailGenerator.model.ValidateReq;
import com.otp.OTPEmailGenerator.model.ValidateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OTPService {

    private static final byte[] SHARED_KEY = "SecretKey".getBytes();
    private static final Map<String, Long> counterMap = new HashMap<>();
    public long getCounter(String username) {
        return counterMap.getOrDefault(username, 0L);
    }
    public void updateCounter(String username, long newCounter) {
            counterMap.put(username, newCounter);
        }

    private final JavaMailSender mailSender;

    public MessageRes generateOTP(OTPReq otpReq) {
        long counter = getCounter(otpReq.getUsername());
        try {
            String otp = HOTPGenerator.generateOTP(SHARED_KEY, counter);
            updateCounter(otpReq.getUsername(), counter + 1);
            try {
                sendOtpByEmail(otpReq.getEmail(), otp);
                return new MessageRes("OTP generated and sent to " + otpReq.getEmail());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new MessageRes("Error while sending email! Try again");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new MessageRes("Error while generating otp! Try again");
        }
    }

    public ValidateRes validateOTP(ValidateReq request) {
        String username = request.getUsername();
        String userOTP = request.getOtp();
        long counter = getCounter(username);
        String generatedOTP = HOTPGenerator.generateOTP(SHARED_KEY, counter - 1);
        if(userOTP.equals(generatedOTP)) {
            return new ValidateRes(true, "OTP authentication successful!");
        }
        return new ValidateRes(false, "OTP authentication failed!");
    }
    public void sendOtpByEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nnh10052002@gmail.com");
        message.setTo(email);
        message.setSubject("OTP Code Message");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }
}
