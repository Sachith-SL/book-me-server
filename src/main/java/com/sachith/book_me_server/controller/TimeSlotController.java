package com.sachith.book_me_server.controller;

import com.sachith.book_me_server.model.TimeSlot;
import com.sachith.book_me_server.service.TimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    // Add new time slot
    @PostMapping
    public ResponseEntity<TimeSlot> addTimeSlot(@RequestBody TimeSlot timeSlot) {
        TimeSlot saved = timeSlotService.addTimeSlot(timeSlot);
        return ResponseEntity.ok(saved);
    }

    // Get all time slots
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        return ResponseEntity.ok(timeSlotService.getAllTimeSlots());
    }

    // Get time slot by id
    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable Long id) {
        return timeSlotService.getTimeSlotById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update time slot
    @PutMapping("/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlot timeSlot) {
        TimeSlot updated = timeSlotService.updateTimeSlot(id, timeSlot);
        return ResponseEntity.ok(updated);
    }

    // Delete time slot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }

    // Get all AVAILABLE time slots
    @GetMapping("/available")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots() {
        List<TimeSlot> availableSlots = timeSlotService.getAvailableTimeSlots();
        return ResponseEntity.ok(availableSlots);
    }
}

