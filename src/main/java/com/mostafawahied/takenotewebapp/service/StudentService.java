package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface StudentService {
    // a helper method to obtain the email address of the logged in user depending on the user is logged in using google or not
    String getUserEmailFromAuthentication(Authentication authentication);

    List<Student> getAllStudents(Authentication authentication);

    void saveStudent(Student student, Long classroomId);

    Student getStudentById(long id);

    void deleteStudentById(long id);

    // Method retrieves all students from the database and returns a new list of students with only their latest meeting
    List<Student> getStudentsWithLastMeeting(Authentication authentication);

    List<Student> getStudentsWithLastMeetingReading(Authentication authentication);

    List<Student> getStudentsWithLastMeetingWriting(Authentication authentication);

    List<Student> getStudentsWithLastAllMeetingByClassroom(Authentication authentication);

    List<Student> getStudentsWithLastWritingMeetingByClassroom(Authentication authentication);

    List<Student> getStudentsWithLastReadingMeetingByClassroom(Authentication authentication);

    List<Student> getAllStudentsBySelectedClassroom(Authentication authentication);

    List<Student> getAllStudentsByClassroomId(Long classroomId);
}
