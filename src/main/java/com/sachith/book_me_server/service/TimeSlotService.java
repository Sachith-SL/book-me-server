package com.sachith.book_me_server.service;

import com.sachith.book_me_server.model.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import com.sachith.book_me_server.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    // Create
    public TimeSlot addTimeSlot(TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }

    // Create by date
    public List<TimeSlot> addTimeSlotByDate(LocalDate date) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (int i = 10; i < 24; i++) {
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setDate(date);
            timeSlot.setStartTime(LocalTime.of(i, 0));
            if(i<17)
                timeSlot.setSlotPrice(BigDecimal.valueOf(3000.0));
            else
                timeSlot.setSlotPrice(BigDecimal.valueOf(4000.0));

            timeSlot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            if(i==23)
                timeSlot.setEndTime(LocalTime.of(0, 0));
            else
                timeSlot.setEndTime(LocalTime.of(i+1, 0));
            timeSlots.add(timeSlotRepository.save(timeSlot));

        }
        return timeSlots;
    }

    // Read all
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    // Read by id
    public Optional<TimeSlot> getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id);
    }

    // Update
    public TimeSlot updateTimeSlot(Long id, TimeSlot updatedTimeSlot) {
        return timeSlotRepository.findById(id).map(existing -> {
            existing.setDate(updatedTimeSlot.getDate());
            existing.setStartTime(updatedTimeSlot.getStartTime());
            existing.setEndTime(updatedTimeSlot.getEndTime());
            existing.setSlotPrice(updatedTimeSlot.getSlotPrice());
            existing.setAvailabilityStatus(updatedTimeSlot.getAvailabilityStatus());
            existing.setDescription(updatedTimeSlot.getDescription());
            return timeSlotRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("TimeSlot not found with id " + id));
    }

    // Delete
    public void deleteTimeSlot(Long id) {
        if(!timeSlotRepository.existsById(id)) {
            throw new RuntimeException("TimeSlot not found with id " + id);
        }
        timeSlotRepository.deleteById(id);
    }

    // Get only AVAILABLE time slots
    public List<TimeSlot> getAvailableTimeSlots() {
        return timeSlotRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
    }

    public List<TimeSlot> getTimeSlotsByDate(LocalDate date) {
        return timeSlotRepository.findByDate(date);
    }

    public List<TimeSlot> getTimeSlotsByDateAndAvailabilityStatus(LocalDate date) {
        return timeSlotRepository.findByDateAndAvailabilityStatus(date,AvailabilityStatus.AVAILABLE);
    }
}

