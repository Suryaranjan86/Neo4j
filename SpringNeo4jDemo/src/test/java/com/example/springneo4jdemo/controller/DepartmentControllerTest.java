package com.example.springneo4jdemo.controller;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.dto.UniversityPayLoadDTO;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.service.DepartmentServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(DepartmentController.class)
class DepartmentControllerTest {
    @MockBean
    private DepartmentServiceImp departmentServiceImp;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void saveDepartment() {
        DepartmentPayLoadDTO departmentPayLoadDTO=DepartmentPayLoadDTO.builder().departmentName("ECE").universityName("BPUT").build();
        Department department=Department.builder()
                .university(University.builder().universityName(departmentPayLoadDTO.getUniversityName()).build())
                .departmentName(departmentPayLoadDTO.getDepartmentName())
                .build();
        Mockito.when(departmentServiceImp.saveDepartment(departmentPayLoadDTO)).thenReturn(department);
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
}