package com.ghbt.ghbt_starbucks.api.event.service;

import com.ghbt.ghbt_starbucks.api.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.api.event.dto.RequestEvent;
import java.util.List;

public interface IEventService {


    void addEvent(RequestEvent requestEvent);

    ResponseEvent getEventById(Long id);

    ResponseEvent getEventByTag(String tag);

    List<ResponseEvent> getEventAll();

}
