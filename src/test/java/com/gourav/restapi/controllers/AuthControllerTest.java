package com.gourav.restapi.controllers;

import com.gourav.restapi.controllers.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.RequestEntity.post;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AuthControllerTest {
    public AuthControllerTest() {
    }

    @Test
    void testRegisterUser() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("newUser");
        signUpRequest.setEmail("newUser@example.com");
        signUpRequest.setPassword("password");
        HashSet<String> roles = new HashSet<>();
        roles.add("admin");
        signUpRequest.setRoles(roles);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<SignupRequest> request = new HttpEntity<>(signUpRequest, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(post(URI.create("http://localhost:8081/api/auth/signup")).body(request), String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}