package com.ghbt.ghbt_starbucks.api.event.service;

import com.ghbt.ghbt_starbucks.api.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.api.event.dto.RequestEvent;

public interface IEventService {


    void addEvent(RequestEvent requestEvent);
    ResponseEvent getEventById(Long id);

    ResponseEvent getEventByName(String name);

}
