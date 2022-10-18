package com.example.springneo4jdemo.controller;

import com.example.springneo4jdemo.dto.CollegePayLoadDTO;
import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.College;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.service.CollegeServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(CollegeController.class)
class CollegeControllerTest {
    @MockBean
    private CollegeServiceImp collegeServiceImp;
    CollegePayLoadDTO collegePayLoadDTO = new CollegePayLoadDTO();
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        collegePayLoadDTO.setCollegeName("KIIT");
        List<DepartmentPayLoadDTO> departmentPayLoadDTOList = new ArrayList<>();
        DepartmentPayLoadDTO departmentPayLoadDTO1 = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO1.setDepartmentName("CSE");
        departmentPayLoadDTOList.add(departmentPayLoadDTO1);
        DepartmentPayLoadDTO departmentPayLoadDTO2 = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO1.setDepartmentName("ECE");
        departmentPayLoadDTOList.add(departmentPayLoadDTO2);
        DepartmentPayLoadDTO departmentPayLoadDTO3 = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO1.setDepartmentName("MECHANICAL");
        departmentPayLoadDTOList.add(departmentPayLoadDTO3);
        collegePayLoadDTO.setDepartmentList(departmentPayLoadDTOList);
        collegePayLoadDTO.setUniversityName("BPUT");
    }

    @Test
    void saveCollege() throws Exception {
        List<Department> departmentList = new ArrayList<>();
        collegePayLoadDTO.getDepartmentList().forEach(department -> {
            departmentList.add(Department.builder().departmentName(department.getDepartmentName()).build());
        });
        College college = College.builder()
                .collegeName(collegePayLoadDTO.getCollegeName())
                .departmentList(departmentList)
                .university(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build())
                .build();
        Mockito.when(collegeServiceImp.saveCollege(collegePayLoadDTO)).thenReturn(college);
        webTestClient.post()
                .uri("/college/savecollege")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(collegePayLoadDTO), Object.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    Assertions.assertNotNull(response.getResponseBody());
                    // Assertions.ass
                });
    }

    @Test
    void updateCollegeWithDEpartment() {
    }

    @Test
    void deleteCollegeById() {
    }

    @Test
    void addDepartment() {
    }
}