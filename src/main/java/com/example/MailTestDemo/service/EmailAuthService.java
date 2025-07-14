package com.example.MailTestDemo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthService {


    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "email:auth:";

    public void sendAuthCode(String email){
        String authCode = generateAuthCode();

        redisTemplate.opsForValue().set(PREFIX + email, authCode, Duration.ofMinutes(5));

        //이메일 전송 로직
        sendEmail(email,authCode);
    }

    public boolean verifyAuthCode(String email, String inputCode){
        String key = PREFIX + email;
        String savedCode = redisTemplate.opsForValue().get(key);

        if(savedCode != null && savedCode.equals(inputCode)){
            redisTemplate.delete(key);
            return true;
        }
        else return false;
    }

    private String generateAuthCode(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void sendEmail(String toEmail, String authCode){

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("이메일 인증 Test입니다.");

            Context context = new Context();
            context.setVariable("authCode", authCode);

            String htmlContent = templateEngine.process("email-auth", context);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
