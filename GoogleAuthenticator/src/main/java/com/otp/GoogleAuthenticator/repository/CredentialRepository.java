package com.otp.GoogleAuthenticator.repository;

import com.otp.GoogleAuthenticator.model.UserTOTP;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CredentialRepository implements ICredentialRepository {
    private final Map<String, UserTOTP> usersKeys = new HashMap<String, UserTOTP>() {{
        put("hoang@actvn", null);
        put("tuan@actvn", null);
    }};

    @Override
    public String getSecretKey(String username) {
        return usersKeys.get(username).getSecretKey();
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {
        usersKeys.put(username, new UserTOTP(username, secretKey, validationCode, scratchCodes));
    }
    public UserTOTP getUser(String username) {
        return usersKeys.get(username);
    }
}
