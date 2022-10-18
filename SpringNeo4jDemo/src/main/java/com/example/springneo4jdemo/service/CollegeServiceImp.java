package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.CollegePayLoadDTO;
import com.example.springneo4jdemo.entity.College;
import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.CollegeRepository;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.UniversityRepository;
import org.neo4j.driver.Driver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CollegeServiceImp implements ICollegeService {

    private final Driver driver;
    private CollegeRepository collegeRepository;
    private DepartmentRepository departmentRepository;

    private UniversityRepository universityRepository;

    public CollegeServiceImp(Driver driver, CollegeRepository collegeRepository, DepartmentRepository departmentRepository, UniversityRepository universityRepository) {
        this.driver = driver;
        this.collegeRepository = collegeRepository;
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public College saveCollege(CollegePayLoadDTO collegePayLoadDTO) throws Exception {
        List<Department> departmentList = new ArrayList<Department>();
        University existingUniversity=universityRepository.findByUniversityName(collegePayLoadDTO.getUniversityName());
        if(existingUniversity==null)
            throw new DataNotFoundExeption(collegePayLoadDTO.getUniversityName() + " " + "is not available");
        if (collegePayLoadDTO.getDepartmentList() != null) {
            collegePayLoadDTO.getDepartmentList().forEach(department -> {
                Department existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());
                if (existingDepartment == null) {
                    throw new DataNotFoundExeption(department.getDepartmentName() + " " + "is not available");
                } else {
                    departmentList.add(existingDepartment);
                }
            });

        }
        College college = College.builder().build();
        college.setCollegeName(collegePayLoadDTO.getCollegeName());
        college.setDepartmentList(departmentList);
        college.setUniversity(existingUniversity);

        return collegeRepository.save(college);
    }

    @Override
    public int updateCollegeWithDEpartment(String collegename, String departmentname, String relationType) throws Exception {
        return collegeRepository.updateCollegeWithSingleDepartment(collegename, departmentname, relationType);
    }

    public void deleteCollegeById(UUID id) throws Exception {
        collegeRepository.deleteById(id);
    }

    @Transactional
    public String addDepartment(CollegePayLoadDTO collegePayLoadDTO) throws Exception {

        //check college is present or not
        College college = collegeRepository.findByCollegeName(collegePayLoadDTO.getCollegeName());
        if (college == null) {
            throw new DataNotFoundExeption("given a wrong college name");
        }
        List<String> departmentList = new ArrayList<>();
        collegePayLoadDTO.getDepartmentList().forEach(departmentPayLoadDTO -> {
            Department existingDepartment = departmentRepository.findByDepartmentName(departmentPayLoadDTO.getDepartmentName());
            if (existingDepartment == null)
                throw new DataNotFoundExeption(departmentPayLoadDTO.getDepartmentName() + " " + "is not available");
            else
                departmentList.add(departmentPayLoadDTO.getDepartmentName());
        });

        return collegeRepository.addDepartment(collegePayLoadDTO.getCollegeName(), departmentList);
    }


}