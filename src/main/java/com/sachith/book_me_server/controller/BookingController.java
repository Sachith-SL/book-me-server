package com.sachith.book_me_server.controller;

import com.sachith.book_me_server.model.Booking;
import com.sachith.book_me_server.model.BookingRequest;
import com.sachith.book_me_server.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Add new booking
    @PostMapping("/{id}")
    public ResponseEntity<Booking> addBookingByCustomerId(
            @RequestBody Booking booking,
            @RequestParam Long customerId,
            @RequestParam List<Long> timeSlotIds) {
        Booking saved = bookingService.addBooking(booking, customerId, timeSlotIds);
        return ResponseEntity.ok(saved);
    }
    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestBody BookingRequest request) {
        Booking saved = bookingService.addBooking(request);
        return ResponseEntity.ok(saved);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestBody Booking booking,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) List<Long> timeSlotIds) {
        Booking updated = bookingService.updateBooking(id, booking, customerId, timeSlotIds);
        return ResponseEntity.ok(updated);
    }

    // Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

