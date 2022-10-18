package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    public StudentService studentService;

    //    JUnit test for find student by id
    @Test
    void getStudentByIdTest() {
        Long actual = 6L;
        Student student = studentService.getStudentById(actual);
        Long expected = student.getId();

        Assertions.assertThat(expected.equals(actual));
    }
}
