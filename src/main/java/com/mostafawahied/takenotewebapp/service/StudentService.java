package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

public interface StudentService {
//    List<Student> getAllStudents ();
//    void saveStudent(Student student);

    List<Student> getAllStudents(Principal principal);

    //    public void saveStudent(Student student) {
//        this.studentRepository.save(student);
//    }
    void saveStudent(Student student, Principal principal);

    Student getStudentById(long id);
    void deleteStudentById(long id);
    Student getStudentByIdByQuery(long id);

    void saveMultipleGuidedReadingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);
    void saveMultipleStrategyReadingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);

    void saveMultipleStrategyWritingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint);


    // get students with last meetings
//    List<Student> getStudentsWithLastMeeting() throws Exception;

    List<Student> getStudentsWithLastMeeting(Principal principal) throws Exception;

    List<Student> getStudentsWithLastMeetingReading(Principal principal) throws Exception;

    List<Student> getStudentsWithLastMeetingWriting(Principal principal) throws Exception;
//    List<Student> findAllStudents();
}
