package com.myprojects.takenotewebapp.service;

import com.myprojects.takenotewebapp.model.Student;
import com.myprojects.takenotewebapp.repository.StudentRepository;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents ();
    void saveStudent(Student student);
    Student getStudentById(long id);
    void deleteStudentById(long id);
    Student getStudentByFirstName(String firstName);

}
