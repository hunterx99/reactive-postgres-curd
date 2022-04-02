package com.example.reactivepostgresdemo.controller;

import com.example.reactivepostgresdemo.model.Customer;
import com.example.reactivepostgresdemo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable Integer id) {
        return customerRepository.findById(id);
    }

    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        return customerRepository.findById(id)
                .map(c -> {
                    c.setName(customer.getName());
                    return c;
                })
                .flatMap(c -> customerRepository.save(c));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return customerRepository.deleteById(id);
    }
}
