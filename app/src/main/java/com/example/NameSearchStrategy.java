package com.example;

import java.util.ArrayList;
import java.util.List;

public class NameSearchStrategy implements SearchStrategy {

    private final StudentService studentService;

    public NameSearchStrategy(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<String[]> search(String query) {
        List<String[]> allStudents = studentService.searchStudents(query);
        List<String[]> result = new ArrayList<>();

        for (String[] student : allStudents) {
            if (student[0].equalsIgnoreCase(query)) {
                result.add(student);
            }
        }
        return result;
    }
}
