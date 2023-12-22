package com.otp.OTPEmailGenerator.service;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {
    private static final byte[] SHARED_KEY = "SecretKey".getBytes();
    private static final Map<String, Long> counterMap = new HashMap<>();
    public long getCounter(String username) {
        return counterMap.getOrDefault(username, 0L);
    }
        public void updateCounter(String username, long newCounter) {
            counterMap.put(username, newCounter);
        }

    public String generateOTP(String username) {
        long counter = getCounter(username);
        String otp = HOTPGenerator.generateOTP(SHARED_KEY, counter);
        updateCounter(username, counter + 1);
        return otp;
    }
    public boolean validateOTP(String username, String userOTP) {
        long counter = getCounter(username);
        String generatedOTP = HOTPGenerator.generateOTP(SHARED_KEY, counter - 1);
        return userOTP.equals(generatedOTP);
    }
}
