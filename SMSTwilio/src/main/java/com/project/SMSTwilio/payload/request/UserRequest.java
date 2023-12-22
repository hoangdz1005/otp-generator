package com.project.SMSTwilio.payload.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String telephoneNumber;
}
