package com.example.MailTestDemo.controller;


import com.example.MailTestDemo.dto.EmailRequestDto;
import com.example.MailTestDemo.dto.EmailVerifyRequestDto;
import com.example.MailTestDemo.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MailAuthTestController {

    private final EmailAuthService emailAuthService;


    @PostMapping("/")
    public ResponseEntity<String> sendCode(@RequestBody EmailRequestDto dto){
        emailAuthService.sendAuthCode(dto.getEmail());
        return ResponseEntity.ok("인증번호 전송 완료!");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody EmailVerifyRequestDto dto){
        boolean isValid = emailAuthService.verifyAuthCode(dto.getEmail(), dto.getAuthCode());

        if (isValid){
            return ResponseEntity.ok("인증 성공!");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패!");
        }
    }
}
