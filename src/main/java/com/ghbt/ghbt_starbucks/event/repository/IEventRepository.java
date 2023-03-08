package com.ghbt.ghbt_starbucks.event.repository;

import com.ghbt.ghbt_starbucks.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event,Long> {

    Event findByName(String name);
}
