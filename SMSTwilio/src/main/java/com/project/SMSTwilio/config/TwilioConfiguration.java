package com.project.SMSTwilio.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwilioConfiguration {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
