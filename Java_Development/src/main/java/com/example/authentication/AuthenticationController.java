package com.example.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthenticationController {
	
	@Value("${authentication.api.url}")
    private String authenticationApiUrl;

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthenticationRequest request) {
        // Prepare the request body
        String requestBody = "{\"login_id\":\"" + request.getLoginId() + "\",\"password\":\"" + request.getPassword() + "\"}";

        // Make a POST request to the authentication API
        RestTemplate restTemplate = new RestTemplate();
        AuthenticationResponse response = restTemplate.postForObject(authenticationApiUrl, requestBody, AuthenticationResponse.class);

        // Extract and return the token
        return response.getToken();
    }

}
