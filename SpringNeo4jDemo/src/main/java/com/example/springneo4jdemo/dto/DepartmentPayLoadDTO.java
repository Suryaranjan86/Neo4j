package com.example.springneo4jdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
@Builder
public class DepartmentPayLoadDTO {

    private UUID deptId;

    private String departmentName;
    private String universityName;
    private List<String> subjectList;
}
