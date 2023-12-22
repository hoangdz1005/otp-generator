package com.project.SMSTwilio.service;

import com.project.SMSTwilio.config.TwilioConfiguration;
import com.project.SMSTwilio.entity.OneTimePassword;
import com.project.SMSTwilio.entity.User;
import com.project.SMSTwilio.payload.request.OTPSmsSender;
import com.project.SMSTwilio.payload.request.UserRequest;
import com.project.SMSTwilio.repository.OneTimePasswordRepository;
import com.project.SMSTwilio.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service("twilioService")
public class TwilioSmsSender implements OTPSmsSender {

    private Map<String, String> otpMap = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;
    private final OTPGenerator otpGenerator;

    @Autowired
    OneTimePasswordRepository oneTimePasswordRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration, OTPGenerator otpGenerator) {
        this.twilioConfiguration = twilioConfiguration;
        this.otpGenerator = otpGenerator;
    }

    @Override
    public void sendSms2(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if (user != null) {
            PhoneNumber to = new PhoneNumber(user.getTelephoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Dear Customer , Your OTP is ##" + otp + "##";
            MessageCreator creator = Message.creator(to, from, otpMessage);
            creator.create();
            OneTimePassword oneTimePassword = new OneTimePassword();
            if (user.getUsername() != null) {
                oneTimePassword.setUser(user);
                oneTimePassword.setCode(otp);
                oneTimePasswordRepository.save(oneTimePassword);
            }
            LOGGER.info("SMS sent to the relevant phone number {} ", oneTimePassword.getUser().getTelephoneNumber());
            LOGGER.info("OTP: " + otp);
        }

    }


    private String generateOTP() {
        String secretKey = otpGenerator.generateSecretKey();
        Random random = new Random();
        long counter = 100000 + random.nextLong(900000);
        return otpGenerator.generateOtp(secretKey, counter);
    }
}
