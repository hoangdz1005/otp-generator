package com.otp.GoogleAuthenticator.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.otp.GoogleAuthenticator.model.ValidationCode;
import com.otp.GoogleAuthenticator.model.ValidationStatus;
import com.otp.GoogleAuthenticator.repository.CredentialRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class GoogleAuthController {

    private final GoogleAuthenticator googleAuth;

    private final CredentialRepository credentialRepository;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public void generate(@PathVariable String username, HttpServletResponse response) {
        final GoogleAuthenticatorKey key = googleAuth.createCredentials(username);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("google-auth-app", username, key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @PostMapping("/validate/key")
    public ValidationStatus validateKey(@RequestBody ValidationCode body) {
        return new ValidationStatus(googleAuth.authorizeUser(body.getUsername(), body.getCode()));
    }

    @GetMapping("/scratches/{username}")
    public List<Integer> getScratchCodes(@PathVariable String username) {
        return credentialRepository.getUser(username).getScratchCodes();
    }

    @PostMapping("/scratches")
    public ValidationStatus validateScratch(@RequestBody ValidationCode body) {
        List<Integer> scratchCodes = getScratchCodes(body.getUsername());
        ValidationStatus validation = new ValidationStatus(scratchCodes.contains(body.getCode()));
        scratchCodes.remove(body.getCode());
        return validation;
    }
}
