package com.myprojects.takenotewebapp.service;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.Student;
import com.myprojects.takenotewebapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MeetingService meetingService;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
        this.studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        Optional<Student> optional = studentRepository.findById(id);

        Student student = null;
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

    @Override
    public Student getStudentByFirstName(String firstName) {
        return studentRepository.findStudentByFirstNameEquals(firstName);
    }

    @Override
    public void saveMultipleGuidedReadingStudents(Meeting meeting, String id, java.sql.Date date, Character readingLevel, String teachingPoint) {
        List<Integer> listIntegerId = new ArrayList<>();
        List<String> listStringId = Arrays.asList(id.split(","));
        for (String s : listStringId) {
            listIntegerId.add(Integer.valueOf(s));
        }
        for (Integer integer : listIntegerId) {
            meeting = new Meeting();
            Student studentById = getStudentById(integer);
            meeting.setStudent(studentById);
            meeting.setDate(date);
            meeting.setSubject("Reading");
            meeting.setType("guided reading");
            meeting.setSubjectLevel(readingLevel);
            meeting.setTeachingPoint(teachingPoint);
            meetingService.saveMeeting(meeting);
        }
    }

    @Override
    public void saveMultipleStrategyReadingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint) {
        List<Integer> listIntegerId = new ArrayList<>();
        List<String> listStringId = Arrays.asList(id.split(","));
        for (String s : listStringId) {
            listIntegerId.add(Integer.valueOf(s));
        }
        for (Integer integer : listIntegerId) {
            meeting = new Meeting();
            Student studentById = getStudentById(integer);
            meeting.setStudent(studentById);
            meeting.setDate(date);
            meeting.setSubject("Reading");
            meeting.setType("Strategy Group");
            meeting.setSubjectLevel(readingLevel);
            meeting.setTeachingPoint(teachingPoint);
            meetingService.saveMeeting(meeting);
        }
    }

    @Override
    public void saveMultipleStrategyWritingStudents(Meeting meeting, String id, Date date, Character readingLevel, String teachingPoint) {
        List<Integer> listIntegerId = new ArrayList<>();
        List<String> listStringId = Arrays.asList(id.split(","));
        for (String s : listStringId) {
            listIntegerId.add(Integer.valueOf(s));
        }
        for (Integer integer : listIntegerId) {
            meeting = new Meeting();
            Student studentById = getStudentById(integer);
            meeting.setStudent(studentById);
            meeting.setDate(date);
            meeting.setSubject("Writing");
            meeting.setType("Strategy Group");
            meeting.setSubjectLevel(readingLevel);
            meeting.setTeachingPoint(teachingPoint);
            meetingService.saveMeeting(meeting);
        }
    }

}