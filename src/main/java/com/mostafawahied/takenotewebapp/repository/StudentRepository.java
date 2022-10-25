package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT id, first_name, last_name from students s where s.id = :id", nativeQuery = true)
    Student getStudentByIdByQuery(long id);
}
