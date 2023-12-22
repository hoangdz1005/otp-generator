package com.otp.OTPEmailGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateRes {
    private boolean valid;
    private String message;
}
