package com.example.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthenticationService {
	
	private final String AUTH_API_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

	public String authenticateUser() {
        // Prepare the request body
        String requestBody = "{\"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\"}";

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send POST request to authenticate user and get the authentication token
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(AUTH_API_URL, new HttpEntity<>(requestBody, headers), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Parse the JSON response to extract the token using Jackson ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                // Assuming the response contains the token in a JSON format like {"token": "your_received_token"}
                String token = rootNode.path("token").asText();
                return token;
            } else {
                System.out.println("Authentication failed. Status code: " + response.getStatusCodeValue());
                System.out.println("Response: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (4xx)
            System.out.println("Authentication failed. Status code: " + e.getRawStatusCode());
            System.out.println("Response: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }

        // Return null if authentication fails
        return null;
	}

}
