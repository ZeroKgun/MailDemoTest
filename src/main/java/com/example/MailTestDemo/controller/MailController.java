package com.example.MailTestDemo.controller;


import com.example.MailTestDemo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/simple")
    public void sendSimpleMailMessage(){
        mailService.sendSimpleMailMessage();
        System.out.println("success!");
    }

    @GetMapping("/mime")
    public void sendMimeMessage(){
        mailService.sendMimeMessage();
    }
}
