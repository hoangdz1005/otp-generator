package com.project.SMSTwilio.service;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Service
public class OTPGenerator {
    private static final int SECRET_SIZE = 10;
    private static final int DIGITS = 6;
    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final int INTERVAL = 30;

    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] secretKey = new byte[SECRET_SIZE];
        random.nextBytes(secretKey);
        return new Base32().encodeAsString(secretKey);
    }

    public String generateOtp(String secretKey, long counter) {
        try {
            byte[] keyBytes = new Base32().decode(secretKey);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(keySpec);

            long counterInterval = counter / INTERVAL;
            byte[] counterBytes = new byte[8];
            for (int i = 7; i >= 0; i--) {
                counterBytes[i] = (byte) (counterInterval & 0xff);
                counterInterval >>= 8;
            }

            byte[] hash = mac.doFinal(counterBytes);

            int offset = hash[hash.length - 1] & 0xf;
            int binary =
                    ((hash[offset] & 0x7f) << 24) |
                            ((hash[offset + 1] & 0xff) << 16) |
                            ((hash[offset + 2] & 0xff) << 8) |
                            (hash[offset + 3] & 0xff);

            int otp = binary % (int) Math.pow(10, DIGITS);
            return String.format("%0" + DIGITS + "d", otp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
