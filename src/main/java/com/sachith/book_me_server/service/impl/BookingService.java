package com.sachith.book_me_server.service.impl;

import com.sachith.book_me_server.model.entity.Booking;
import com.sachith.book_me_server.model.dto.BookingRequest;
import com.sachith.book_me_server.model.entity.Customer;
import com.sachith.book_me_server.model.entity.TimeSlot;
import com.sachith.book_me_server.model.enums.AvailabilityStatus;
import com.sachith.book_me_server.model.enums.BookingStatus;
import com.sachith.book_me_server.repository.BookingRepository;
import com.sachith.book_me_server.repository.CustomerRepository;
import com.sachith.book_me_server.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final TimeSlotRepository timeSlotRepository;


    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          TimeSlotRepository timeSlotRepository, TimeSlotService timeSlotService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    // Create Booking
    @Transactional
    public Booking addBooking(BookingRequest request) {
        if(request == null){
            throw new RuntimeException("Request  is null");
        }

        if(request.getDate().isBefore(LocalDate.now()) || request.getDate().isEqual(LocalDate.now())){
            throw new RuntimeException("Date is expired");
        }

        // Save Customer
        Customer newCustomer = new Customer(request.getName(), request.getPhone());
        Customer customer = customerRepository.save(newCustomer);

        // Fetch all available time slots
        List<TimeSlot> availableSlots = timeSlotRepository.findByDateAndAvailabilityStatus(request.getDate(), AvailabilityStatus.AVAILABLE);

        // Filter requested IDs that are available
        List<TimeSlot> timeSlotsToBook = availableSlots.stream()
                .filter(ts -> request.getSlotIds().contains(ts.getId()))
                .toList();

        if (timeSlotsToBook.isEmpty() || timeSlotsToBook.size() != request.getSlotIds().size()) {
            throw new RuntimeException("One or more selected time slots are not available");
        }

        Booking booking = new Booking();

        // Set customer and time slots
        booking.setCustomer(customer);
        booking.setTimeSlots(timeSlotsToBook);

        // Calculate total price
        BigDecimal totalPrice = timeSlotsToBook.stream()
                .map(TimeSlot::getSlotPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        booking.setTotalPrice(totalPrice);

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setSportType(request.getSportType());

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Mark booked slots as BOOKED
        timeSlotsToBook.forEach(ts -> {
            ts.setAvailabilityStatus(AvailabilityStatus.BOOKED);
            timeSlotRepository.save(ts);
        });

        return savedBooking;
    }




    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Update Booking
    public Booking updateBooking(Long id, Booking updatedBooking, Long customerId, List<Long> timeSlotIds) {
        return bookingRepository.findById(id).map(existing -> {
            // Update customer if provided
            if(customerId != null) {
                var customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException("Customer not found"));
                existing.setCustomer(customer);
            }

            // Update time slots if provided
            if(timeSlotIds != null && !timeSlotIds.isEmpty()) {
                var timeSlots = timeSlotRepository.findAllById(timeSlotIds);
                existing.setTimeSlots(timeSlots);
                // Recalculate total price
                BigDecimal totalPrice = timeSlots.stream()
                        .map(TimeSlot::getSlotPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                existing.setTotalPrice(totalPrice);
            }

            // Update status and sport type
            existing.setBookingStatus(updatedBooking.getBookingStatus());
            existing.setSportType(updatedBooking.getSportType());

            return bookingRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // Delete Booking
    public void deleteBooking(Long id) {
        if(!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

//    TODO:implement this
//    public Booking getBookingByPhone(String phone) {
//        return bookingRepository.findById(phone);
//    }
}

