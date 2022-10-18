package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.UniversityPayLoadDTO;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.repository.UniversityRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UniversityServiceImpTest {
    @Mock
    private UniversityRepository universityRepository;
    @InjectMocks
    private UniversityServiceImp universityServiceImp;

    @Test
    void saveUniversity() throws Exception {
        UniversityPayLoadDTO universityPayLoadDTO=UniversityPayLoadDTO.builder().universityName("BPUT").build();
        University university=University.builder().universityName(universityPayLoadDTO.getUniversityName()).build();
        Mockito.when( universityRepository.save(university)).thenReturn(university);
        Assertions.assertThat(universityServiceImp.saveUniversity(universityPayLoadDTO)).isNotNull();
    }
}