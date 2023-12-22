package com.project.SMSTwilio.service;

import com.project.SMSTwilio.payload.request.OTPSmsSender;
import com.project.SMSTwilio.payload.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final OTPSmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilioService") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSmsByService(UserRequest userRequest) {
        smsSender.sendSms2(userRequest);
    }

}
