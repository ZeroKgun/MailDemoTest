package com.example.MailTestDemo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try{
            //메일을 받을 수신자 설정
            simpleMailMessage.setTo("maxgun98@naver.com");
            //메일 제목 설정
            simpleMailMessage.setSubject("테스트 메일 제목");
            //메일 내용 설정
            simpleMailMessage.setText("愛してる!!!!");
            javaMailSender.send(simpleMailMessage); // 💥 이게 빠졌음
            log.info("단순 메일 전송 완료");
        } catch(Exception e){
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    public void sendMimeMessage(){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            //메일을 받을 수신자 설정
            mimeMessageHelper.setTo("maxgun98@naver.com");
            //메일의 제목 설정
            mimeMessageHelper.setSubject("mime 메세지 제목");

            String content = """
                    <html>
                    
                    <body>
                        <h1> 테스트 메일 </h1>
                        <br>
                    
                        <h3> 테스트 메일 내용 </h3>
                    </body>
                    
                    </html>
                    """;
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("MIME 메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }
}
