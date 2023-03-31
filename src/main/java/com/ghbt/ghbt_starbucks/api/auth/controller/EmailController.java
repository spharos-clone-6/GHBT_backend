package com.ghbt.ghbt_starbucks.api.auth.controller;

import com.ghbt.ghbt_starbucks.api.auth.dto.RequestEmailAuthCode;
import com.ghbt.ghbt_starbucks.api.auth.dto.RequestEmail;
import com.ghbt.ghbt_starbucks.api.auth.dto.ResponseEmailAuthCode;
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
    public ResponseEntity<ResponseEmailAuthCode> getEmailCode(
        @RequestBody RequestEmail requestEmail,
        @Value("${spring.mail.username}") String adminEmail) {

        ResponseEmailAuthCode responseEmailAuthCode = emailService.sendStarbucksAuthEmail(requestEmail.getEmail(), adminEmail);

        return ResponseEntity.status(HttpStatus.OK)
            .body(responseEmailAuthCode);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateEmailCode(@RequestBody RequestEmailAuthCode requestEmailAuthCode) {

        if (emailService.isValidateAuthCode(requestEmailAuthCode.getEmail(), requestEmailAuthCode.getAuthCode())) {
            return ResponseEntity.status(HttpStatus.OK)
                .build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
        }
    }

    @PostMapping("/duplicate")
    public ResponseEntity<?> duplicateEmailCode(@RequestBody RequestEmail requestEmail) {
        if (emailService.isDuplicateEmail(requestEmail.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                .build();
        }
    }
}
