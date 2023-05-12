package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.StudentRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Student> getAllStudents(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (user != null) {
            return user.getStudents();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveStudent(Student student, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        student.setUser(user);
        this.studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        Optional<Student> optional = studentRepository.findById(id);

        Student student;
        if (optional.isPresent()) {
            student = optional.get();
        } else {
            throw new RuntimeException(("Student not found for id: " + id));
        }
        return student;
    }

    @Override
    public void deleteStudentById(long id) {
        studentRepository.deleteById(id);
    }

    // Method retrieves all students from the database and returns a new list of students with only their latest meeting
    @Override
    public List<Student> getStudentsWithLastMeeting(Principal principal) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {
            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> meetings = student.getMeetings();
            Meeting meeting = meetings.stream().max(meetingDateComparator).orElse(new Meeting());
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
        }
        return newStudentsList;
    }

    @Override
    public List<Student> getStudentsWithLastMeetingReading(Principal principal) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {
            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> allMeetings = student.getMeetings();
            List<Meeting> readingMeetings = new ArrayList<>();
            for (Meeting readingMeeting : allMeetings) {
                if (Objects.equals(readingMeeting.getSubject(), "Reading")) {
                    readingMeetings.add(readingMeeting);
                }
            }
            Meeting meeting = readingMeetings.stream().max(meetingDateComparator).orElse(new Meeting());
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
        }
        return newStudentsList;
    }

    @Override
    public List<Student> getStudentsWithLastMeetingWriting(Principal principal) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {
            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> allMeetings = student.getMeetings();
            List<Meeting> readingMeetings = new ArrayList<>();
            for (Meeting readingMeeting : allMeetings) {
                if (Objects.equals(readingMeeting.getSubject(), "Writing")) {
                    readingMeetings.add(readingMeeting);
                }
            }
            Meeting meeting = readingMeetings.stream().max(meetingDateComparator).orElse(new Meeting());
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
        }
        return newStudentsList;
    }

}