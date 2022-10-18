package com.example.springneo4jdemo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;
import java.util.UUID;
@Data
@Builder
@Node(primaryLabel = "Department")
public class Department {
    @Id
    @GeneratedValue
    private UUID deptId;
    @Property(name = "department_name")
    private String departmentName;
    @Relationship(type = "HAVING_DEPART", direction = Relationship.Direction.INCOMING)
    private University university;
}
