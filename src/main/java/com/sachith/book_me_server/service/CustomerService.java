package com.sachith.book_me_server.service;

import com.sachith.book_me_server.model.Customer;
import com.sachith.book_me_server.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Create
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Read all
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Read by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Update
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(existing -> {
            existing.setName(updatedCustomer.getName());
            existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
            return customerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }

    // Delete
    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}

