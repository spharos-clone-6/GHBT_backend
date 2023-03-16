package com.ghbt.ghbt_starbucks.api.auth.controller;

import com.ghbt.ghbt_starbucks.api.auth.dto.RequestAuthCode;
import com.ghbt.ghbt_starbucks.api.auth.dto.RequestEmail;
import com.ghbt.ghbt_starbucks.api.auth.dto.ResponseAuthCode;
import com.ghbt.ghbt_starbucks.api.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/email")
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<ResponseAuthCode> getEmailCode(
        @RequestBody RequestEmail requestEmail,
        @Value("${spring.mail.username}") String adminEmail) {

        ResponseAuthCode responseAuthCode = emailService.sendStarbucksAuthEmail(requestEmail.getEmail(), adminEmail);

        return ResponseEntity.status(HttpStatus.OK)
            .body(responseAuthCode);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateEmailCode(@RequestBody RequestAuthCode requestAuthCode) {
        if (emailService.isValidateAuthCode(requestAuthCode.getEmail(), requestAuthCode.getAuthCode())) {
            return ResponseEntity.status(HttpStatus.OK)
                .build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
        }
    }
}
