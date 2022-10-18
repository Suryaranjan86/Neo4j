package com.example.springneo4jdemo.controller;

import com.example.springneo4jdemo.dto.DepartmentPayLoadDTO;
import com.example.springneo4jdemo.service.DepartmentServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private DepartmentServiceImp departmentServiceImp;

    public DepartmentController(DepartmentServiceImp departmentServiceImp) {
        this.departmentServiceImp = departmentServiceImp;
    }

    @PostMapping("/savedepartment")
    public ResponseEntity<?> saveDepartment(@RequestBody DepartmentPayLoadDTO departmentPayLoadDTO) {
        ResponseEntity<?> entity = new ResponseEntity<>(departmentServiceImp.saveDepartment(departmentPayLoadDTO), HttpStatus.OK);
        return entity;
    }

    @PostMapping("/addsubjects")
    public ResponseEntity<?> addSubjectToDepartment(@RequestBody DepartmentPayLoadDTO departmentPayLoadDTO) {
        List<String> subjectList = departmentPayLoadDTO.getSubjectList();
        ResponseEntity<?> entity = new ResponseEntity<>(departmentServiceImp.addSubjectInDepartment(departmentPayLoadDTO.getDepartmentName(), subjectList), HttpStatus.OK);
        return entity;
    }
}
