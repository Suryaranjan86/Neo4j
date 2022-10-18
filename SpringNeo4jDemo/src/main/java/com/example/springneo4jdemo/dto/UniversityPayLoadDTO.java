package com.example.springneo4jdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UniversityPayLoadDTO {
    private UUID universityId;
    private String universityName;
}
