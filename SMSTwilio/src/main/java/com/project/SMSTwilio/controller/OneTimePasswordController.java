package com.project.SMSTwilio.controller;

import com.project.SMSTwilio.entity.OneTimePassword;
import com.project.SMSTwilio.service.OneTimePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class OneTimePasswordController {

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @GetMapping
    public List<OneTimePassword> main() {
        return oneTimePasswordService.getAll();
    }


}
