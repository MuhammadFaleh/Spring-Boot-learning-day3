package com.lab5.studentsGpa.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentSystem {
    private String id;
    private String name;
    private int age;
    private String degree;
    private double gpa;
    private String honorsCategory;
}
