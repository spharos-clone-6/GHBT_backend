package com.ghbt.ghbt_starbucks.api.event.controller;

import com.ghbt.ghbt_starbucks.api.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.api.event.service.IEventService;
import com.ghbt.ghbt_starbucks.api.event.dto.RequestEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
    @Operation(summary = "이벤트 작성", description = "requestbody에 입력해 주세요")
    public void addEvent(@RequestBody RequestEvent requestEvent) {
        iEventService.addEvent(requestEvent);
    }

    @GetMapping("/{id}")
    @Operation(summary = "이벤트 id로 검색", description = "파라미터에 id 입력해주세요.")
    public ResponseEvent getEventById(@PathVariable Long id) {
        return iEventService.getEventById(id);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "이벤트 이름으로 검색", description = "파라미터에 name 입력해주세요.")
    public ResponseEvent getEventByName(@PathVariable String name) {
        return iEventService.getEventByName(name);
    }

    @GetMapping()
    @Operation(summary = "이벤트 리스트 전체 검색", description = "이벤트 리스트 전체 검색합니다.")
    public List<ResponseEvent> getEventAll() {
        return iEventService.getEventAll();
    }
}
