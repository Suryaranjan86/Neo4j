package com.example.springneo4jdemo.service;


import com.example.springneo4jdemo.dto.StudentPayLoadDTO;
import com.example.springneo4jdemo.entity.*;
import com.example.springneo4jdemo.exception.DataNotFoundExeption;
import com.example.springneo4jdemo.repository.CollegeRepository;
import com.example.springneo4jdemo.repository.DepartmentRepository;
import com.example.springneo4jdemo.repository.StudentRepository;
import com.example.springneo4jdemo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImp implements IStudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CollegeRepository collegeRepository;

    @Override
    public Student saveStudent(StudentPayLoadDTO studentPayLoadDTO) throws Exception {
        //validate College

        College college= collegeRepository.findByCollegeName(studentPayLoadDTO.getCollegeName());
        if(college==null)
            throw new DataNotFoundExeption("College is not available");

        // validate department
        Department department = departmentRepository.findDepartmentByCollege(studentPayLoadDTO.getDepartmentPayLoadDTO().getDepartmentName(), studentPayLoadDTO.getCollegeName());
        if (department == null)
            throw new DataNotFoundExeption("Department is not available for this college");
        Student student = Student.builder().age(studentPayLoadDTO.getAge())
                .name(studentPayLoadDTO.getName())
                .department(department)
                .isLearningList(studentPayLoadDTO.getIsLearningPayLoads().stream().map(learning -> IsLearning.builder().subject(Subject.builder().subjectName(learning.getSubjectName()).build()).build()).collect(Collectors.toList())).
                college(college)
                .build();
        Student saveStudent = studentRepository.save(student);
        return saveStudent;
    }

    //find student by nodeId

    public Student findById(UUID id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent())
            return studentOptional.get();
        else
            return null;
    }

    // find student by name

    public List<Student> findByName(String name) {
        return studentRepository.findByName(name);
    }


    public void delete(UUID id) {

        studentRepository.delete(id);
    }

}
