package com.sachith.book_me_server.service;

import com.sachith.book_me_server.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    // Create
    public Customer addCustomer(Customer customer);

    // Read all
    public List<Customer> getAllCustomers();

    // Read by ID
    public Optional<Customer> getCustomerById(Long id);

    // Update
    public Customer updateCustomer(Long id, Customer updatedCustomer);

    // Delete
    public void deleteCustomer(Long id);
}
