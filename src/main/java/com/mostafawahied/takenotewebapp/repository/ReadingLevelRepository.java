package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.ReadingLevel;
import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingLevelRepository extends JpaRepository<ReadingLevel, Long> {
    ReadingLevel findTopByStudentOrderByIdDesc(Student student);
}
