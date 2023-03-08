package com.ghbt.ghbt_starbucks.event.controller;

import com.ghbt.ghbt_starbucks.event.dto.RequestEvent;
import com.ghbt.ghbt_starbucks.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.event.service.IEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이벤트")
@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final IEventService iEventService;

    @PostMapping()
    public void addEvent(@RequestBody RequestEvent requestEvent){
        iEventService.addEvent(requestEvent);
    }

    @GetMapping("/{id}")
    public ResponseEvent getEventById(@PathVariable Long id){
        return iEventService.getEventById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEvent getEventByName(@PathVariable String name){
        return iEventService.getEventByName(name);
    }



}
