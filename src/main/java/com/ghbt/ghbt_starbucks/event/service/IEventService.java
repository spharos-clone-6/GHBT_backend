package com.ghbt.ghbt_starbucks.event.service;

import com.ghbt.ghbt_starbucks.event.dto.RequestEvent;
import com.ghbt.ghbt_starbucks.event.dto.ResponseEvent;

public interface IEventService {


    void addEvent(RequestEvent requestEvent);
    ResponseEvent getEventById(Long Id);

    ResponseEvent getEventByName(String name);

}
