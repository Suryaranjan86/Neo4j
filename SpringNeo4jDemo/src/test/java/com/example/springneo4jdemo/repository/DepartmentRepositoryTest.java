/*
package com.example.springneo4jdemo.repository;

import com.example.springneo4jdemo.entity.Department;
import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.it.Neo4JContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DepartmentRepositoryTest extends Neo4JContainer {
@Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void findByDepartmentName() {
    }

    @Test
    void findDepartmentByCollege() {
    }

    @Test
    void addSubjectInDepartment() {
    }
@Test
    public void saveDepartment(){
        University university=University.builder().universityName("BPUT").build();
        Department department=Department.builder().build();
        department.setDepartmentName("ECE");
        department.setUniversity(university);
       Department saveDepartMent= departmentRepository.save(department);
        Assertions.assertThat(saveDepartMent).isNotNull();
        Assertions.assertThat(saveDepartMent.getDeptId()).isNotNull();
    }
}*/
