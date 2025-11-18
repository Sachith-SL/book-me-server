package com.sachith.book_me_server.controller;

import com.sachith.book_me_server.model.entity.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import com.sachith.book_me_server.service.TimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final TimeSlotService timeSlotService;

    public AdminController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    // Add new time slot
    @PostMapping("timeslots")
    public ResponseEntity<TimeSlot> addTimeSlot(@RequestBody TimeSlot timeSlot) {
        TimeSlot saved = timeSlotService.addTimeSlot(timeSlot);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("timeslots/date")
    public ResponseEntity<List<TimeSlot>> addTimeSlotByDate(@RequestParam LocalDate date) {
        List<TimeSlot> saved = timeSlotService.addTimeSlotByDate(date);
        return ResponseEntity.ok(saved);
    }

    // Update time slot
    @PutMapping("timeslots/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlot timeSlot) {
        TimeSlot updated = timeSlotService.updateTimeSlot(id, timeSlot);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("timeslots/{id}")
    public ResponseEntity<TimeSlot> reserve(@PathVariable Long id, @RequestBody AvailabilityStatus status) {
        TimeSlot updated = timeSlotService.updateTimeSlotByStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // Delete time slot
    @DeleteMapping("timeslots/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }
}
