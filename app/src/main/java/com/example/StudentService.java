package com.example;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final DbHelper db;

    public StudentService(DbHelper dbHelper) {
        this.db = dbHelper;
    }



    // Command Interface
    private interface Command<T> {
        T execute();
    }

    // Invoker Method
    private <T> T executeCommand(Command<T> command) {
        return command.execute();
    }

    public boolean idExists(String roll) {
        return executeCommand(() -> db.idExists(roll));
    }

    public boolean insertStudent(String roll, String name) {
        return executeCommand(() -> db.insertStudent(roll, name));
    }

    public List<String[]> searchStudents(String query) {
        return executeCommand(() -> db.searchStudents(query));
    }
}
