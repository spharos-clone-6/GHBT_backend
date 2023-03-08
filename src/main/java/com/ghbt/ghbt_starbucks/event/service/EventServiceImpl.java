package com.ghbt.ghbt_starbucks.event.service;

import com.ghbt.ghbt_starbucks.event.dto.RequestEvent;
import com.ghbt.ghbt_starbucks.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.event.model.Event;
import com.ghbt.ghbt_starbucks.event.repository.IEventRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService{

   private final IEventRepository iEventRepository;

    @Override
    public void addEvent(RequestEvent requestEvent) {
        Event event = Event.builder()
                .description(requestEvent.getDescription())
                .descriptionUrl(requestEvent.getDescriptionUrl())
                .thumbnailUrl(requestEvent.getThumbnailUrl())
                .name(requestEvent.getName())
                .build();
        iEventRepository.save(event);
    }

    @Override
    public ResponseEvent getEventById(Long id) {
        Event event = iEventRepository.findById(id).get();

        return ResponseEvent.builder()
                .description(event.getDescription())
                .descriptionUrl(event.getDescriptionUrl())
                .id(event.getId())
                .name(event.getName())
                .thumbnailUrl(event.getThumbnailUrl())
                .build();
    }

    @Override
    public ResponseEvent getEventByName(String name) {
        return null;
    }
}
