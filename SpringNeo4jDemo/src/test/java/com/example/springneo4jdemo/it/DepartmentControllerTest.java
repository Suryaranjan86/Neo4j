/*
package com.example.springneo4jdemo.it;

import com.example.springneo4jdemo.controller.DepartmentController;
import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.service.DepartmentServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepartmentControllerTest extends Neo4JContainer {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveDepartment() {
        DepartmentPayLoadDTO departmentPayLoadDTO=DepartmentPayLoadDTO.builder().departmentName("ECE").universityName("BPUT").build();
        Department department=Department.builder()
                .university(University.builder().universityName(departmentPayLoadDTO.getUniversityName()).build())
                .departmentName(departmentPayLoadDTO.getDepartmentName())
                .build();
        webTestClient.post()
                .uri("/department/savedepartment")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(departmentPayLoadDTO),Object.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(University.class)
                .consumeWith(response -> {
                    Assertions.assertNotNull(response.getResponseBody());
                    // Assertions.ass
                });
    }
}*/
