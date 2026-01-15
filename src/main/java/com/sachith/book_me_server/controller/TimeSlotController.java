package com.sachith.book_me_server.controller;

import com.sachith.book_me_server.model.dto.ApiResponse;
import com.sachith.book_me_server.model.entity.TimeSlot;
import com.sachith.book_me_server.service.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
@CrossOrigin(origins = "*")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
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

    // Get all AVAILABLE time slots
    @GetMapping("/available")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots() {
        List<TimeSlot> availableSlots = timeSlotService.getAvailableTimeSlots();
        return ResponseEntity.ok(availableSlots);
    }

    //    Get all time slots by date
    @GetMapping("date")
    public ResponseEntity<ApiResponse<List<TimeSlot>>> getTimeSlotsByDate(@RequestParam LocalDate date) {
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByDateAndAvailabilityStatus(date);
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.name(), timeSlots));
    }

}

