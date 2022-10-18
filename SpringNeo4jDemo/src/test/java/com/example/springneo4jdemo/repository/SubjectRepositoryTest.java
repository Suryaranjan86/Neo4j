/*
package com.example.springneo4jdemo.repository;

import com.example.springneo4jdemo.entity.Student;
import com.example.springneo4jdemo.it.Neo4JContainer;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SubjectRepositoryTest extends Neo4JContainer {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findBySubjectName() {
    }

    @Test
    public void givenStudentObjectWhenSaveReturnStudentObject() {
        //given
        Student student = Student.builder()
                .name("Surya")
                .age(20)
                .bloodGroup("B+").build();
        //when
        studentRepository.save(student);
        //then
        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getStudentId()).isNotNull();
    }
}*/
