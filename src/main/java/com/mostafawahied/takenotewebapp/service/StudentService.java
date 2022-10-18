package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;

import java.sql.Date;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents ();
    void saveStudent(Student student);
    Student getStudentById(long id);
    void deleteStudentById(long id);
    Student getStudentByIdByQuery(long id);

    void saveMultipleGuidedReadingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);
    void saveMultipleStrategyReadingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);

    void saveMultipleStrategyWritingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);


    // get students with last meetings
    List<Student> getStudentsWithLastMeeting();
//    List<Student> findAllStudents();
}
