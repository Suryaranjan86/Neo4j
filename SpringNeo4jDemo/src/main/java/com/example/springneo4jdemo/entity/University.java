package com.example.springneo4jdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Node(primaryLabel = "University")
public class University {
    @Id
    @GeneratedValue
    private UUID universityId;
    private String universityName;

}
