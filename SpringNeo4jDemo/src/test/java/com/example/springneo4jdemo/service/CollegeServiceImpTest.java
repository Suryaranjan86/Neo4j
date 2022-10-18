package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.CollegePayLoadDTO;
import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.College;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.CollegeRepository;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.UniversityRepository;
import com.example.springneo4jdemo.service.CollegeServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CollegeServiceImpTest {
    CollegePayLoadDTO collegePayLoadDTO = new CollegePayLoadDTO();
    @Mock
    private CollegeRepository collegeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UniversityRepository universityRepository;
    @InjectMocks
    private CollegeServiceImp collegeService;

    @BeforeEach
    public void setUpData() {

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
    @DisplayName("Test for invalid college  data")
    public void givenCollegePayLoadDTOWhenaddDepartmentThenReturnCollegeNotFoundExceptionTest() throws Exception {
        //given
        CollegePayLoadDTO collegePayLoadDTO = new CollegePayLoadDTO();
        collegePayLoadDTO.setCollegeName("KIIT");
        // when
        Mockito.when(collegeRepository.findByCollegeName(collegePayLoadDTO.getCollegeName())).thenReturn(null);
        assertThatThrownBy(() -> collegeService.addDepartment(collegePayLoadDTO)).isInstanceOf(DataNotFoundExeption.class).hasMessage("given a wrong college name");
    }

    @Test
    @DisplayName("Test for invalid Department  data")
    public void givenCollegePayLoadDTOWhenaddDepartmentThenReturnDepartMentNotFoundExceptionTest() throws Exception {
        // when
        Mockito.when(collegeRepository.findByCollegeName(collegePayLoadDTO.getCollegeName())).thenReturn(College.builder().collegeName(collegePayLoadDTO.getCollegeName()).build());
        Mockito.when(departmentRepository.findByDepartmentName(collegePayLoadDTO.getDepartmentList().get(0).getDepartmentName())).thenReturn(null);
       /* Throwable exception = assertThrows(DataNotFoundExeption.class, () -> collegeService.addDepartment(collegePayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo(collegePayLoadDTO.getDepartmentList().get(0).getDepartmentName() + " " + "is not available");*/
        assertThatThrownBy(() -> collegeService.addDepartment(collegePayLoadDTO)).isInstanceOf(DataNotFoundExeption.class).hasMessage(collegePayLoadDTO.getDepartmentList().get(0).getDepartmentName() + " " + "is not available");
    }

    @Test
    @DisplayName("Test for Successfully add Department")
    public void givenCollegePayLoadDTOWhenaddDepartmentThenReturnSusscessMessageTest() throws Exception {
        //given


        List<String> departmentList = new ArrayList<>();
        collegePayLoadDTO.getDepartmentList().forEach(departmentPayLoadDTO -> {
            departmentList.add(departmentPayLoadDTO.getDepartmentName());
        });
        College college = College.builder().build();
        collegePayLoadDTO.getDepartmentList().forEach(department -> {
                    Department department1 = Department.builder().departmentName(department.getDepartmentName()).build();
                    Mockito.when(departmentRepository.findByDepartmentName(department.getDepartmentName())).thenReturn(department1);
                }
        );

        // when
        Mockito.when(collegeRepository.findByCollegeName(collegePayLoadDTO.getCollegeName())).thenReturn(college);
        Mockito.when(collegeRepository.addDepartment(collegePayLoadDTO.getCollegeName(), departmentList)).thenReturn("department is added to college");
        assertEquals("department is added to college", collegeService.addDepartment(collegePayLoadDTO));
    }

    @Test
    public void giveCollegePayLoadDTOWhensaveCollegeReturnCollegeObject() throws Exception {
        List<Department> departmentList = new ArrayList<Department>();
        Mockito.when(universityRepository.findByUniversityName(collegePayLoadDTO.getUniversityName())).thenReturn(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build());
        collegePayLoadDTO.getDepartmentList().forEach(department -> {
                    Department department1 = Department.builder().departmentName(department.getDepartmentName()).build();
                    departmentList.add(department1);
                    Mockito.when(departmentRepository.findByDepartmentName(department.getDepartmentName())).thenReturn(department1);
                }
        );
        College college = College.builder().collegeName(collegePayLoadDTO.getCollegeName()).university(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build()).departmentList(departmentList).build();
        Mockito.when(collegeRepository.save(college)).thenReturn(college);
        Assertions.assertThat(collegeService.saveCollege(collegePayLoadDTO)).isNotNull();
        Assertions.assertThat(collegeService.saveCollege(collegePayLoadDTO).getCollegeName()).isNotNull();
    }

    @Test
    public void giveCollegePayLoadDTOwithnulldepartmentWhensaveCollegeReturnCollegeObject() throws Exception {
        List<Department> departmentList = new ArrayList<Department>();
        collegePayLoadDTO.setDepartmentList(null);
        College college = College.builder().collegeName(collegePayLoadDTO.getCollegeName()).university(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build()).departmentList(departmentList).build();
        Mockito.when(universityRepository.findByUniversityName(collegePayLoadDTO.getUniversityName())).thenReturn(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build());
        Mockito.when(collegeRepository.save(college)).thenReturn(college);
        Assertions.assertThat(collegeService.saveCollege(collegePayLoadDTO)).isNotNull();
        Assertions.assertThat(collegeService.saveCollege(collegePayLoadDTO).getCollegeName()).isNotNull();
    }

    @Test
    public void giveCollegePayLoadDTOWhensaveCollegeReturnDepartmentNotFoundException() throws Exception {
        Mockito.when(universityRepository.findByUniversityName(collegePayLoadDTO.getUniversityName())).thenReturn(University.builder().universityName(collegePayLoadDTO.getUniversityName()).build());
        Mockito.when(departmentRepository.findByDepartmentName(collegePayLoadDTO.getDepartmentList().get(0).getDepartmentName())).thenReturn(null);
        Throwable exception = assertThrows(DataNotFoundExeption.class, () -> collegeService.saveCollege(collegePayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo(collegePayLoadDTO.getDepartmentList().get(0).getDepartmentName() + " " + "is not available");
    }
    @Test
    public void giveCollegePayLoadDTOWhensaveCollegeReturnUniversityNotFoundException() throws Exception {
        Mockito.when(universityRepository.findByUniversityName(collegePayLoadDTO.getUniversityName())).thenReturn(null);
        Throwable exception = assertThrows(DataNotFoundExeption.class, () -> collegeService.saveCollege(collegePayLoadDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo(collegePayLoadDTO.getUniversityName() + " " + "is not available");
    }
}