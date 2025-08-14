package com.sachith.book_me_server.service;

import com.sachith.book_me_server.model.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import com.sachith.book_me_server.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
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
}

