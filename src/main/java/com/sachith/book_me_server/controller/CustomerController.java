package com.sachith.book_me_server.controller;

import com.sachith.book_me_server.model.dto.ApiResponse;
import com.sachith.book_me_server.model.entity.Customer;
import com.sachith.book_me_server.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Add new customer
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer saved = customerService.addCustomer(customer);
        return ResponseEntity.ok(saved);
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.name(),customers));
    }

    // Get customer by id
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }

    // Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

