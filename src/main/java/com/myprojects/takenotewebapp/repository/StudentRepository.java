package com.myprojects.takenotewebapp.repository;

import com.myprojects.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
Student findStudentByFirstNameEquals(String firstName);

}
