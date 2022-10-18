package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.dto.IsLearningPayLoad;
import com.example.springneo4jdemo.dto.StudentPayLoadDTO;
import com.example.springneo4jdemo.dto.SubjectPayLoadDTO;
import com.example.springneo4jdemo.entity.*;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.CollegeRepository;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImpTest {
    @InjectMocks
    private StudentServiceImp studentServiceImp;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private CollegeRepository collegeRepository;
    private StudentPayLoadDTO studentPayLoadDTO;

    @BeforeEach
    public void setupData() {
        studentPayLoadDTO = new StudentPayLoadDTO();
        studentPayLoadDTO.setName("Surya");
        studentPayLoadDTO.setAge(20);
        studentPayLoadDTO.setBloodGroup("B+");
        studentPayLoadDTO.setCollegeName("KIIT");
        DepartmentPayLoadDTO departmentPayLoadDTO = DepartmentPayLoadDTO.builder().build();
        departmentPayLoadDTO.setDepartmentName("ECE");

        IsLearningPayLoad isLearningPayLoad = new IsLearningPayLoad();
        isLearningPayLoad.setSubjectName("Java");

        IsLearningPayLoad isLearningPayLoad1 = new IsLearningPayLoad();
        isLearningPayLoad1.setSubjectName("Python");

        studentPayLoadDTO.setDepartmentPayLoadDTO(departmentPayLoadDTO);
        studentPayLoadDTO.setIsLearningPayLoads(List.of(isLearningPayLoad, isLearningPayLoad1));
    }


    @Test
    void givenStudentPayLoadDTOWhenSaveReturnStudentObject() throws Exception {

        Student student = Student.builder().age(studentPayLoadDTO.getAge())
                .name(studentPayLoadDTO.getName()).department(Department.builder().departmentName(studentPayLoadDTO.getDepartmentPayLoadDTO().getDepartmentName()).build())
                .isLearningList(studentPayLoadDTO.getIsLearningPayLoads().stream().map(learning -> IsLearning.builder().subject(Subject.builder().subjectName(learning.getSubjectName()).build()).build()).collect(Collectors.toList())).
                college(College.builder().collegeName(studentPayLoadDTO.getCollegeName()).build())
                .build();


        //when
        Mockito.when(collegeRepository.findByCollegeName(studentPayLoadDTO.getCollegeName())).thenReturn(College.builder().collegeName(studentPayLoadDTO.getCollegeName()).build());
        Mockito.when(departmentRepository.findDepartmentByCollege(student.getDepartment().getDepartmentName(), student.getCollege().getCollegeName())).thenReturn(Department.builder().departmentName(student.getDepartment().getDepartmentName()).build());
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        //then
        Student saveStudent = studentServiceImp.saveStudent(studentPayLoadDTO);
        Assertions.assertThat(saveStudent).isNotNull();
    }

    @Test
    void givenStudentPayLoadDTOWhenSaveReturnDepartmentNotFoundException() {

        //when
        Mockito.when(collegeRepository.findByCollegeName(studentPayLoadDTO.getCollegeName())).thenReturn(College.builder().collegeName(studentPayLoadDTO.getCollegeName()).build());

        Mockito.when(departmentRepository.findDepartmentByCollege(studentPayLoadDTO.getDepartmentPayLoadDTO().getDepartmentName(), studentPayLoadDTO.getCollegeName())).thenReturn(null);

        //then
        // Assertions.assertThatExceptionOfType(DataNotFoundExeption.class).isThrownBy(studentServiceImp.saveStudent(studentPayLoadDTO))
        Throwable exception = assertThrows(DataNotFoundExeption.class, () -> studentServiceImp.saveStudent(studentPayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Department is not available for this college");
    }

    @Test
    void givenStudentPayLoadDTOWhenSaveReturnCollegeNotFoundException() {

        //when
        Mockito.when(collegeRepository.findByCollegeName(studentPayLoadDTO.getCollegeName())).thenReturn(null);

        //then
        // Assertions.assertThatExceptionOfType(DataNotFoundExeption.class).isThrownBy(studentServiceImp.saveStudent(studentPayLoadDTO))
        Throwable exception = assertThrows(DataNotFoundExeption.class, () -> studentServiceImp.saveStudent(studentPayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo("College is not available");
    }
}