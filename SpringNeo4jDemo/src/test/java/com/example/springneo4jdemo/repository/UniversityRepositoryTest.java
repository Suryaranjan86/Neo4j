package com.example.springneo4jdemo.repository;

import com.example.springneo4jdemo.entity.University;
import com.example.springneo4jdemo.it.Neo4JContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UniversityRepositoryTest extends Neo4JContainer {
    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void saveUniversity() {
        University university = University.builder().build();
        university.setUniversityName("BPUT");
        University saveUniversity = universityRepository.save(university);
        Assertions.assertThat(saveUniversity).isNotNull();
        Assertions.assertThat(saveUniversity.getUniversityId()).isNotNull();
    }


}
