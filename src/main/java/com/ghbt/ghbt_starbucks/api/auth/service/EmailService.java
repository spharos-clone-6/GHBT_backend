package com.ghbt.ghbt_starbucks.api.auth.service;

import com.ghbt.ghbt_starbucks.api.auth.dto.ResponseEmailAuthCode;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
import com.ghbt.ghbt_starbucks.global.security.redis.RedisService;
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
    private final RedisService redisService;
    private final IUserRepository iUserRepository;
    private final long EXPIRED_AUTH_MILLISECONDS = 1000 * 60 * 3; //3분

    public ResponseEmailAuthCode sendStarbucksAuthEmail(String clientEmail, String adminEmail) {
        StarBucksAuthEmail starBucksAuthEmail = StarBucksAuthEmail
            .builder()
            .emailSender(emailSender)
            .to(clientEmail)
            .from(adminEmail)
            .build();
        String authCode = starBucksAuthEmail.sendEmail();
        redisService.setValuesWithTimeout("EMAIL(" + clientEmail + ")", authCode, EXPIRED_AUTH_MILLISECONDS);

        return new ResponseEmailAuthCode(authCode);
    }

    public boolean isValidateAuthCode(String clientEmail, String authCode) {
        String findAuthCode = redisService.getValues("EMAIL(" + clientEmail + ")");
        return (findAuthCode != null && findAuthCode.equals(authCode));
    }

    public Boolean isDuplicateEmail(String email) {
        return iUserRepository.findByEmail(email).isPresent();
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
                messageHelper.setText(
                    "<!DOCTYPE html>"
                        + "<head>"
                        + "</head>"
                        + "<body>"
                        + "<!-- 회색 배경 -->"
                        + "<table border='0' cellpadding='0' cellspacing='0' width='100%' bgColor='#FFFFFF' style='padding: 20px 16px 82px; color: #191919;' class='wrapper'>"
                        + "<tbody style='display: block; max-width: 600px; margin: 0 auto;'>"
                        + "<tr width='100%' style='display: block;'>"
                        + "<td width='100%' style='display: block;'>"
                        + "<!-- 본문 -->"
                        + "<table width='100%' border='0' cellpadding='0' cellspacing='0' bgColor='#FFFFFF'style='display: inline-block; ; padding: 32px; text-align: left; border-bottom: 8px solid #036635; border-top: 8px solid #036635; border-collapse: collapse;' class='container'>"
                        + "<tbody style='display: block;'>"
                        + "<!-- 스타벅스 로고 -->"
                        + "<tr>"
                        + "<td style='padding-bottom: 20px; text-align: center; font-size: 20px; font-weight: bold; width: 536px;'>"
                        + "<img width=350sspx src='https://www.vectorlogo.zone/logos/starbucks/starbucks-ar21.png' />"
                        + "</td>"
                        + "</tr>"
                        + "<!-- 본문 제목 -->"
                        + "<tr>"
                        + "</tr>"
                        + "<!-- 본문 내용 -->"

                        + "<!-- 본문 컨텐츠 영역 -->"
                        + "<tr width='100%' style='display: block; margin-bottom: 32px;'>"
                        + "<td width='100%' style='display: block; text-align: -webkit-center;'>"
                        + "<table border='0' cellpadding='0' cellspacing='0' width='70%' bgColor='#e1f7e1' style='padding: 40px 20px; border-radius: 4px; text-align: center; margin-top: 10px;' class='content'>"
                        + "<tbody style='display: block;'>"
                        + "<tr style='display: block;'>"
                        + "<td style='display: block; padding-bottom: 5px; font-size: 40px; font-weight: bold; letter-spacing: 15px;'>"
                        + authCode
                        + "</td>"
                        + "</tr>"
                        + "<tr style='display: block;text-align:center;'>"
                        + "<p style='text-align: center;line-height: 3.0; font-family:fantasy'>"
                        + "&nbsp;스타벅스 온라인 스토어에 오신 걸 환영합니다.<br>"
                        + "&nbsp;회원가입을 위한 <b>인증번호</b> 입니다."
                        + "</p>"
                        + "</tr>"
                        + "</tbody>"
                        + "</table>"
                        + "</td>"
                        + "</tr>"
                        + "<!-- 발신전용 & 저작권 -->"
                        + "</tbody>"
                        + "</table>"
                        + "<td style='padding: 5px 0 0 310px; color: #A7A7A7; font-size: 7px;'>"
                        + "© 2023 SSG INC Spharos Academy GHBT. All Rights Reserved."
                        + "</td>"
                        + "</td>"
                        + "</tr>"
                        + "</tbody>"
                        + "</table>"
                        + "</body>"
                        + "</html>"
                    , true);

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
            this.authCode = String.valueOf(random.nextInt(888888) + 111111); //111111 ~ 999999 생성.
            log.info("[인증번호] : " + this.authCode);
        }
    }
}
