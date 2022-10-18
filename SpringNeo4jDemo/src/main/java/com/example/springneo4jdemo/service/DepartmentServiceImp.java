package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImp implements  IDepartmentService{

    private DepartmentRepository departmentRepository;

    private UniversityRepository universityRepository;

    public DepartmentServiceImp(DepartmentRepository departmentRepository, UniversityRepository universityRepository) {
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public String addSubjectInDepartment(String departmentName, List<String> subjectList) {

         return departmentRepository.addSubjectInDepartment(departmentName,subjectList);
    }

    @Override
    public Department saveDepartment(DepartmentPayLoadDTO departmentPayLoadDTO) {
        University university=universityRepository.findByUniversityName(departmentPayLoadDTO.getUniversityName());
       /* if(university==null)
            throw new DataNotFoundExeption("University is not found");*/
        Department department=Department.builder().departmentName(departmentPayLoadDTO.getDepartmentName())
                .university(university)
                .build();
        department= departmentRepository.save(department);
        return department;
    }

    @Override
    public String addSubjects(List<String> subjectlist) {
        return null;
    }
}
