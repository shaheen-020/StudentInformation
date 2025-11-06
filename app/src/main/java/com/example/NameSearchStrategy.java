package com.example;

import java.util.List;

public class NameSearchStrategy implements SearchStrategy {
    private final StudentService studentService;

    public NameSearchStrategy(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<String[]> search(String query) {
        return studentService.searchStudents(query);
    }
}
