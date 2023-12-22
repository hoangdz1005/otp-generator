package com.project.SMSTwilio.payload.request;

import lombok.Data;

@Data
public class SignOTPRequest {
    private String username;
    private String code;
}