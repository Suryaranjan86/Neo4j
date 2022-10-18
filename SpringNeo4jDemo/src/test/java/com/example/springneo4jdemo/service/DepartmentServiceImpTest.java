package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.UniversityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.hamcrest.collection.ArrayAsIterableMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImpTest {
    @InjectMocks
    private DepartmentServiceImp departmentServiceImp;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UniversityRepository universityRepository;


    @Test
    void givenDepartmentPayLoadDTOObjectWhenSaveThenReturnDepartmentObject() {
        DepartmentPayLoadDTO departmentPayLoadDTO = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO.setDepartmentName("ECE");
        departmentPayLoadDTO.setUniversityName("BPUT");
        University university = University.builder().universityName(departmentPayLoadDTO.getUniversityName()).build();
        Department department = Department.builder().departmentName(departmentPayLoadDTO.getDepartmentName())
                .university(university)
                .build();
        Mockito.when(universityRepository.findByUniversityName(departmentPayLoadDTO.getUniversityName())).thenReturn(university);
        Mockito.when(departmentRepository.save(department)).thenReturn(department);
        Department saveDepartment = departmentServiceImp.saveDepartment(departmentPayLoadDTO);
        Assertions.assertThat(saveDepartment).isNotNull();
    }

    @Test
    @Disabled
    void givenDepartmentPayLoadDTOObjectWhenSaveThenUniversityNotFoundException() {
        DepartmentPayLoadDTO departmentPayLoadDTO = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO.setDepartmentName("ECE");
        departmentPayLoadDTO.setUniversityName("BPUT");
        Department department = Department.builder().departmentName(departmentPayLoadDTO.getDepartmentName())
                .university(University.builder().universityName(departmentPayLoadDTO.getUniversityName()).build())
                .build();
        Mockito.when(universityRepository.findByUniversityName(departmentPayLoadDTO.getUniversityName())).thenReturn(null);
        Throwable exception = assertThrows(DataNotFoundExeption.class, () -> departmentServiceImp.saveDepartment(departmentPayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo("University is not found");
    }

@Test
    public void givenListOfSubjectWhenAddSubjectReturnSuccessMessage() {
        List<String> subjectList = Arrays.asList("Java", "C++", "C");
        String departmentName = "ECE";
        Mockito.when(departmentRepository.addSubjectInDepartment(departmentName, subjectList)).thenReturn("subject is added to department");
        Assertions.assertThat(departmentServiceImp.addSubjectInDepartment(departmentName, subjectList)).isEqualTo("subject is added to department");
    }
}