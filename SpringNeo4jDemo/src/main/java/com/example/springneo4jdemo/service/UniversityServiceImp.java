package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.UniversityPayLoadDTO;
import com.example.springneo4jdemo.entity.College;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.repository.CollegeRepository;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityServiceImp implements IUniversityService {

    private UniversityRepository universityRepository;

    private CollegeRepository collegeRepository;

    private DepartmentRepository departmentRepository;

    public UniversityServiceImp(UniversityRepository universityRepository, CollegeRepository collegeRepository, DepartmentRepository departmentRepository) {
        this.universityRepository = universityRepository;
        this.collegeRepository = collegeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public University saveUniversity(UniversityPayLoadDTO universityPayLoadDTO) throws Exception {
        University university = University.builder().build();
        university.setUniversityName(universityPayLoadDTO.getUniversityName());
        return universityRepository.save(university);
    }
}
