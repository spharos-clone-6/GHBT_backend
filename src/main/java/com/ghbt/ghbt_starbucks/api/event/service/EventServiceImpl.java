package com.ghbt.ghbt_starbucks.api.event.service;

import com.ghbt.ghbt_starbucks.api.event.dto.RequestEvent;
import com.ghbt.ghbt_starbucks.api.event.dto.ResponseEvent;
import com.ghbt.ghbt_starbucks.api.event.model.Event;
import com.ghbt.ghbt_starbucks.api.event.repository.IEventRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final IEventRepository iEventRepository;

    @Override
    public void addEvent(RequestEvent requestEvent) {
        Event event = Event.builder()
            .description(requestEvent.getDescription())
            .descriptionUrl(requestEvent.getDescriptionUrl())
            .thumbnailUrl(requestEvent.getThumbnailUrl())
            .tag(requestEvent.getTag())
            .name(requestEvent.getName()).build();
        iEventRepository.save(event);
    }

    @Override
    public ResponseEvent getEventById(Long id) {
        Event event = iEventRepository.findById(id)
            .orElseThrow(() -> new ServiceException("등록된 이벤트가 없습니다..", HttpStatus.NO_CONTENT));

        return ResponseEvent.builder()
            .description(event.getDescription())
            .descriptionUrl(event.getDescriptionUrl())
            .id(event.getId())
            .name(event.getName())
            .tag(event.getTag())
            .thumbnailUrl(event.getThumbnailUrl()).build();
    }

    @Override
    public ResponseEvent getEventByTag(String tag) {
        Event event = iEventRepository.findByTag(tag);

        return ResponseEvent.builder()
            .description(event.getDescription())
            .descriptionUrl(event.getDescriptionUrl())
            .id(event.getId()).name(event.getName())
            .tag(event.getTag())
            .thumbnailUrl(event.getThumbnailUrl()).build();
    }

    @Override
    public List<ResponseEvent> getEventAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<Event> events = iEventRepository.findAll();

        List<ResponseEvent> responseEvents = new ArrayList<>();
        events.forEach(event -> {
            ResponseEvent responseEvent = modelMapper.map(event, ResponseEvent.class);
            responseEvents.add(responseEvent);
        });
        return responseEvents;
    }
}
