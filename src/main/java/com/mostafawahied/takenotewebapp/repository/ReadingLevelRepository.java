package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.ReadingLevel;
import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface ReadingLevelRepository extends JpaRepository<ReadingLevel, Long> {
    ReadingLevel findTopByStudentOrderByIdDesc(Student student);
    void deleteReadingLevelByStudentIdAndUpdateDate(long id, Date date);

    ReadingLevel findTopByStudentIdAndUpdateDate(long id, Date date);
}
