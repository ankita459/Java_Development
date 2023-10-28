package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.service.AuthenticationService;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
    private AuthenticationService authService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate() {
        String authToken = authService.authenticateUser();
        if (authToken != null) {
            return ResponseEntity.ok(authToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed");
        }
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestHeader("Authorization") String authToken,
                                                 @RequestBody Customer customer) {
        customerService.createCustomer(authToken, customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomerList(@RequestHeader("Authorization") String authToken) {
        List<Customer> customers = customerService.getCustomerList(authToken);
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteCustomer(@RequestHeader("Authorization") String authToken,
                                                 @PathVariable String uuid) {
        customerService.deleteCustomer(authToken, uuid);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateCustomer(@RequestHeader("Authorization") String authToken,
                                                 @PathVariable String uuid,
                                                 @RequestBody Customer customer) {
        customerService.updateCustomer(authToken, uuid, customer);
        return ResponseEntity.ok("Successfully Updated");
    }

}
