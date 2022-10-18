package com.example.springneo4jdemo.controller;

import com.example.springneo4jdemo.dto.UniversityPayLoadDTO;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.service.UniversityServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureWebTestClient(timeout = "10000")
@WebFluxTest(UniversityController.class)
class UniversityControllerTest {
    @MockBean
    private UniversityServiceImp universityServiceImp;
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void saveUniversity() throws Exception {
        UniversityPayLoadDTO universityPayLoadDTO=UniversityPayLoadDTO.builder().universityName("BPUT").build();
        University university=University.builder().universityName(universityPayLoadDTO.getUniversityName()).build();
        Mockito.when(universityServiceImp.saveUniversity(universityPayLoadDTO)).thenReturn(university);
        webTestClient.post()
                .uri("/university/saveUniversity")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(universityPayLoadDTO),Object.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(University.class)
                .consumeWith(response -> {
                    Assertions.assertNotNull(response.getResponseBody());
                    // Assertions.ass
                });
    }
}