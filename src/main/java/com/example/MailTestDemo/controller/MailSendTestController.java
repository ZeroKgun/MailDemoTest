package com.example.MailTestDemo.controller;


import com.example.MailTestDemo.service.MailSendTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailSendTestController {

    private final MailSendTestService mailSendTestService;

    @GetMapping("/simple")
    public void sendSimpleMailMessage(){
        mailSendTestService.sendSimpleMailMessage();
        System.out.println("success!");
    }

    @GetMapping("/mime")
    public void sendMimeMessage(){
        mailSendTestService.sendMimeMessage();
        System.out.println("success!");
    }
}
