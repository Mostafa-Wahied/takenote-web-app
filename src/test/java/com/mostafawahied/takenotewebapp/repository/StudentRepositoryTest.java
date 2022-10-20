package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.TakenoteWebAppApplication;
import com.mostafawahied.takenotewebapp.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TakenoteWebAppApplication.class)
//@RunWith(SpringRunner.class)
public class StudentRepositoryTest {
    @Autowired
    public StudentRepository studentRepository;

//    JUnit test for save Student
    @Test
    void saveStudentTest() {
        Student actual = new Student(35L,"firstNameTest2", "lastNameTest2");
        Student expected = studentRepository.save(actual);

        Assertions.assertThat(expected.equals(actual));
    }

//    JUnit test for find student by firstname
    @Test
    void getStudentByIdTest() {
        Long actual = 29L;
        Student student = studentRepository.getStudentByIdByQuery(actual);
        Long expected = student.getId();

        Assertions.assertThat(expected.equals(actual));
    }
}
