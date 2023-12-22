package com.otp.OTPEmailGenerator.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HOTPGenerator {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final int LENGTH_OF_OTP = 6;

    public static String generateOTP(byte[] key, long counter) {
        try {
            // Chuyển bộ đếm thành mảng byte
            byte[] counterBytes = ByteBuffer.allocate(Long.BYTES).putLong(counter).array();
            // Tạo SecretKeySpec từ khóa bí mật và thuật toán HMAC
            SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_ALGORITHM);

            // Khởi tạo MAC
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(keySpec);
            // Áp dụng HMAC-SHA1 cho bộ đếm
            byte[] hmacResult = mac.doFinal(counterBytes);

            // Lấy 4 byte cuối cùng của kết quả HMAC
            int offset = hmacResult[hmacResult.length - 1] & 0x0F;
            int truncatedHash = ByteBuffer.wrap(hmacResult, offset, 4).getInt() & 0x7FFFFFFF;

            // Lấy độ dài mã OTP
            int otp = truncatedHash % (int) Math.pow(10, LENGTH_OF_OTP);

            // Chuyển số thành string OTP
            return String.format("%0" + LENGTH_OF_OTP + "d", otp);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}


