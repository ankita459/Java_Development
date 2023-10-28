package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.model.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
	
	private final String API_BASE_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    public void createCustomer(String authToken, Customer customer) {
    	// Prepare the request headers with Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        // Prepare the request body
        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        // Send POST request to create a new customer
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(API_BASE_URL, requestEntity, String.class);

        // Handle the response if needed
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Customer created successfully: " + response.getBody());
        } else {
            System.out.println("Failed to create customer. Status code: " + response.getStatusCodeValue());
            System.out.println("Response: " + response.getBody());
        }
       
    }

    public List<Customer> getCustomerList(String authToken) {
        // Prepare the request headers with Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        // Send GET request to get customer list
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "?cmd=get_customer_list", String.class, headers);

        // Parse JSON response and return list of Customer objects
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<Customer> customerList = objectMapper.readValue(responseBody, new TypeReference<List<Customer>>() {});
                return customerList;
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }

        // Handle the case when the request fails or JSON parsing fails
        return null;
    }

    public void deleteCustomer(String authToken, String uuid) {
        // Prepare the request headers with Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        // Prepare the request body (if required)
        String requestBody = "{\"uuid\": \"" + uuid + "\"}";

        // Send POST request to delete customer by UUID
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Uncomment this line if you need to send a request body along with the UUID
            // ResponseEntity<String> response = restTemplate.postForEntity(API_BASE_URL + "?cmd=delete", new HttpEntity<>(requestBody, headers), String.class);

            // Use this line if no request body is required
            ResponseEntity<String> response = restTemplate.postForEntity(API_BASE_URL + "?cmd=delete&uuid=" + uuid, new HttpEntity<>(headers), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Customer with UUID " + uuid + " deleted successfully.");
            } else {
                System.out.println("Failed to delete customer. Status code: " + response.getStatusCodeValue());
                System.out.println("Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (4xx)
            System.out.println("Failed to delete customer. Status code: " + e.getRawStatusCode());
            System.out.println("Response: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    public void updateCustomer(String authToken, String uuid, Customer customer) {
    	
    	// Check if the customer object or its mandatory properties are null
        if (customer == null || customer.getFirstName() == null || customer.getLastName() == null) {
            System.out.println("Failed to update customer. Missing mandatory properties.");
            return;
        }
        // Prepare the request headers with Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the request body
        String requestBody = "{"
                + "\"cmd\": \"update\","
                + "\"uuid\": \"" + uuid + "\","
                + "\"first_name\": \"" + customer.getFirstName() + "\","
                + "\"last_name\": \"" + customer.getLastName() + "\","
                + "\"street\": \"" + customer.getStreet() + "\","
                + "\"address\": \"" + customer.getAddress() + "\","
                + "\"city\": \"" + customer.getCity() + "\","
                + "\"state\": \"" + customer.getState() + "\","
                + "\"email\": \"" + customer.getEmail() + "\","
                + "\"phone\": \"" + customer.getPhone() + "\""
                + "}";

        // Send POST request to update customer by UUID
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_BASE_URL, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Customer with UUID " + uuid + " updated successfully.");
            } else if (response.getStatusCode().value() == 400) {
                System.out.println("Failed to update customer. Body is empty.");
            } else if (response.getStatusCode().value() == 500) {
                System.out.println("Failed to update customer. UUID not found.");
            } else {
                System.out.println("Failed to update customer. Status code: " + response.getStatusCodeValue());
                System.out.println("Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (4xx)
            System.out.println("Failed to update customer. Status code: " + e.getRawStatusCode());
            System.out.println("Response: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }
    

}
