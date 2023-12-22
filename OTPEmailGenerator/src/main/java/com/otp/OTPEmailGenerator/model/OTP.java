package com.otp.OTPEmailGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    private String otp;
    private long timestamp;
}
