package com.example.springneo4jdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;
import java.util.UUID;
@Data
@Node(primaryLabel = "Subject")
@Builder
public class Subject {
    @Id
    @GeneratedValue
    private UUID subjectId;
    @Property(name = "subject_name")
    private String subjectName;
    @Relationship(type = "HAVING_SUBJECT",direction = Relationship.Direction.INCOMING)
    private List<Department> department;

}
