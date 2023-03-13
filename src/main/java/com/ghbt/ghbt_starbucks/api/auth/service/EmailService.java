package com.ghbt.ghbt_starbucks.api.auth.service;

import com.ghbt.ghbt_starbucks.api.auth.dto.ResponseAuthCode;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public ResponseAuthCode sendStarbucksAuthEmail(String clientEmail, String adminEmail) {
        StarBucksAuthEmail starBucksAuthEmail = StarBucksAuthEmail
            .builder()
            .emailSender(emailSender)
            .to(clientEmail)
            .from(adminEmail)
            .build();
        String authCode = starBucksAuthEmail.sendEmail();
        return new ResponseAuthCode(authCode);
    }

    @Builder
    static class StarBucksAuthEmail {

        private final String DOMAIN_NAME = "스타벅스";
        private final String TITLE = "[스타벅스] 회원가입용 이메일 인증코드 발송 안내";
        private JavaMailSender emailSender;
        private String from;
        private String to;
        private String authCode;

        public String sendEmail() {
            try {
                createAuthCode();
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

                messageHelper.setSubject(TITLE);
                messageHelper.setFrom(new InternetAddress(from, DOMAIN_NAME));
                messageHelper.setTo(to);
                messageHelper.setText(authCode, false);

                emailSender.send(message);
                return authCode;

            } catch (MessagingException e) {
                log.error(e.getMessage());
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
            return null;
        }

        public void createAuthCode() {
            Random random = new Random();
            this.authCode = String.valueOf(random.nextInt(888888) + 111111);
            log.info("[인증번호] : " + this.authCode);
        }
    }
}
