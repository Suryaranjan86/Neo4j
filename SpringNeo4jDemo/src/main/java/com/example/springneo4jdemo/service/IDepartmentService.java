package com.example.springneo4jdemo.service;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.entity.Department;

import java.util.List;

public interface IDepartmentService {

    public String addSubjectInDepartment(String departmentName, List<String> subjectList);

    public Department saveDepartment(DepartmentPayLoadDTO departmentPayLoadDTO);

    public String addSubjects(List<String> subjectlist);
}
