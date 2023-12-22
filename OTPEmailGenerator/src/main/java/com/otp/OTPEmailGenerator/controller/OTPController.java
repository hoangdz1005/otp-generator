package com.otp.OTPEmailGenerator.controller;

import com.otp.OTPEmailGenerator.model.ValidateRequest;
import com.otp.OTPEmailGenerator.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
public class OTPController {

    private final OTPService otpService;
    private final JavaMailSender mailSender;

    @Autowired
    public OTPController(OTPService otpService, JavaMailSender mailSender) {
        this.otpService = otpService;
        this.mailSender = mailSender;
    }

    @GetMapping("/generate-otp")
    public String generateOTP(@RequestParam String username, String email) {
        String otp = otpService.generateOTP(username);
        sendOtpByEmail(email, otp);
        return "OTP generated and sent to " + email;
    }

    @PostMapping("/validate-otp")
    public String validateOTP(@RequestBody ValidateRequest request) {
        String username = request.getUsername();
        String userOTP = request.getOtp();
        if (otpService.validateOTP(username, userOTP)) {
            return "OTP is valid!";
        } else {
            return "Invalid OTP!";
        }
    }

    private void sendOtpByEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nnh10052002@gmail.com");
        message.setTo(email);
        message.setSubject("OTP Code Message");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }
}
