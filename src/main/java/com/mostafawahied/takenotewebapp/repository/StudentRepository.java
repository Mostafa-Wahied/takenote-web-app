package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByFirstNameEquals(String firstName);
}
