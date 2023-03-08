package com.ghbt.ghbt_starbucks.health_checker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class HealthChecker {
    @GetMapping()
    public String healthChecker(){
        return "hello";
    }
}
