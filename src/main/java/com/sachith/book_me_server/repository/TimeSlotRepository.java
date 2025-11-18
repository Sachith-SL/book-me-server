package com.sachith.book_me_server.repository;

import com.sachith.book_me_server.model.entity.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);
    List<TimeSlot> findByDate(LocalDate date);
    List<TimeSlot> findByDateAndAvailabilityStatus(LocalDate date,AvailabilityStatus availabilityStatus);

}

