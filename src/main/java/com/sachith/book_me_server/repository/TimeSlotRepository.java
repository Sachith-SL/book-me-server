package com.sachith.book_me_server.repository;

import com.sachith.book_me_server.model.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // Find all time slots with availability AVAILABLE
    List<TimeSlot> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);
}

