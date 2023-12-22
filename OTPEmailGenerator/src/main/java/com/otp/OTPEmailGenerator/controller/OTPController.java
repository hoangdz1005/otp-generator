package com.otp.OTPEmailGenerator.controller;

import com.otp.OTPEmailGenerator.model.MessageRes;
import com.otp.OTPEmailGenerator.model.OTPReq;
import com.otp.OTPEmailGenerator.model.ValidateReq;
import com.otp.OTPEmailGenerator.model.ValidateRes;
import com.otp.OTPEmailGenerator.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OTPController {

    private final OTPService otpService;
    @PostMapping("/generate-otp")
    public ResponseEntity<MessageRes> generateOTP(@RequestBody OTPReq otpReq) {
        return new ResponseEntity<>(otpService.generateOTP(otpReq), HttpStatus.OK);
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<ValidateRes> validateOTP(@RequestBody ValidateReq request) {
       return new ResponseEntity<>(otpService.validateOTP(request), HttpStatus.OK);
    }
}
