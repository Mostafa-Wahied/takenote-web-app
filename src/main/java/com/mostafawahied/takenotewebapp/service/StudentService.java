package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents(Principal principal);

    void saveStudent(Student student, Principal principal);

    Student getStudentById(long id);

    void deleteStudentById(long id);

    List<Student> getStudentsWithLastMeeting(Principal principal) throws Exception;

    List<Student> getStudentsWithLastMeetingReading(Principal principal) throws Exception;

    List<Student> getStudentsWithLastMeetingWriting(Principal principal) throws Exception;
}
