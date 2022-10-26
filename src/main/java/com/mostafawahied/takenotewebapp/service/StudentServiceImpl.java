package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.StudentRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Student> getAllStudents(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return user.getStudents();
    }

    @Override
//    public void saveStudent(Student student) {
//        this.studentRepository.save(student);
//    }
    public void saveStudent(Student student, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        student.setUser(user);
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
    public Student getStudentByIdByQuery(long id) {
        return studentRepository.getStudentByIdByQuery(id);
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
            meeting.setType("Strategy Group - Reading");
            meeting.setSubjectLevel(readingLevel);
            meeting.setTeachingPoint(teachingPoint);
            meetingService.saveMeeting(meeting);
        }
    }

    @Override
    public void saveMultipleStrategyWritingStudents(Meeting meeting, String id, Date date, String teachingPoint) {
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
            meeting.setType("Strategy Group - Writing");
            meeting.setTeachingPoint(teachingPoint);
            meetingService.saveMeeting(meeting);
        }
    }

    @Override
    public List<Student> getStudentsWithLastMeeting(Principal principal) throws Exception {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {
            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> meetings = student.getMeetings();
            Meeting meeting = meetings.stream().max(meetingDateComparator).orElse(new Meeting());
//            Meeting meeting = meetings.stream().max(meetingDateComparator).orElseThrow(()
//                    -> new Exception("****Meetings not found****"));
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
//            System.out.println("******&&&&&&&&&&*****************");
//            System.out.println(meeting.getDate());
//            System.out.println("******&&&&&&&&&&*****************");
        }
        return newStudentsList;
    }

    @Override
    public List<Student> getStudentsWithLastMeetingReading(Principal principal) throws Exception {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {

//            Comparator<Meeting> meetingSubjectComparator = Comparator.comparing(Meeting::getSubject);

            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> allMeetings = student.getMeetings();
            List<Meeting> readingMeetings = new ArrayList<>();
            for ( Meeting readingMeeting : allMeetings ) {
                if (Objects.equals(readingMeeting.getSubject(), "Reading")) {
                    readingMeetings.add(readingMeeting);
                }
            }
            Meeting meeting = readingMeetings.stream().max(meetingDateComparator).orElse(new Meeting());
//            Meeting meeting = meetings.stream().max(meetingDateComparator).orElseThrow(()
//                    -> new Exception("****Meetings not found****"));
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
//            System.out.println("******&&&&&&&&&&*****************");
//            System.out.println(readingMeetings);
//            System.out.println("******&&&&&&&&&&*****************");
        }
        return newStudentsList;
    }

    @Override
    public List<Student> getStudentsWithLastMeetingWriting(Principal principal) throws Exception {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(principal);
        List<Student> newStudentsList = new ArrayList<>();
        for (Student student : studentsList) {

//            Comparator<Meeting> meetingSubjectComparator = Comparator.comparing(Meeting::getSubject);

            Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
            List<Meeting> allMeetings = student.getMeetings();
            List<Meeting> readingMeetings = new ArrayList<>();
            for ( Meeting readingMeeting : allMeetings ) {
                if (Objects.equals(readingMeeting.getSubject(), "Writing")) {
                    readingMeetings.add(readingMeeting);
                }
            }
            Meeting meeting = readingMeetings.stream().max(meetingDateComparator).orElse(new Meeting());
//            Meeting meeting = meetings.stream().max(meetingDateComparator).orElseThrow(()
//                    -> new Exception("****Meetings not found****"));
            student.setMeetings(List.of(meeting));
            newStudentsList.add(student);
//            System.out.println("******&&&&&&&&&&*****************");
//            System.out.println(readingMeetings);
//            System.out.println("******&&&&&&&&&&*****************");
        }
        return newStudentsList;
    }

}