package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.config.CustomOAuth2User;
import com.mostafawahied.takenotewebapp.exception.ResourceNotFoundException;
import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.ClassroomRepository;
import com.mostafawahied.takenotewebapp.repository.StudentRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final UserService userService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ClassroomRepository classroomRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.userService = userService;
    }

    @Override
    public List<Student> getAllStudents(Authentication authentication) {
        // Obtain the email address of the user from the CustomOAuth2User object
        User user = userService.getUser(authentication);
        if (user != null) {
            List<Classroom> classrooms = classroomRepository.findByUser(user);
            return classrooms.stream()
                    .flatMap(classroom -> classroom.getStudents().stream())
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveStudent(Student student, Long classroomId, Authentication authentication) {
        // Obtain the email address of the user from the CustomOAuth2User object
        User user = userService.getUser(authentication);
        if (classroomId != null) {
            Classroom classroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + classroomId));
            classroom.setUser(user);
            student.setClassroom(classroom);
        }
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
            throw new ResourceNotFoundException("Student not found for id: " + id);
        }
        return student;
    }

    @Override
    public void deleteStudentById(long id) {
        studentRepository.deleteById(id);
    }

    // Method retrieves all students from the database and returns a new list of students with only their latest meeting
    @Override
    public List<Student> getStudentsWithLastMeeting(Authentication authentication) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(authentication);
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
    public List<Student> getStudentsWithLastMeetingReading(Authentication authentication) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(authentication);
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
    public List<Student> getStudentsWithLastMeetingWriting(Authentication authentication) {
        //from each student in students list retrieved from DB
        List<Student> studentsList = getAllStudents(authentication);
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

    @Override
    public List<Student> getStudentsWithLastAllMeetingByClassroom(Authentication authentication) {
        long selectedClassroomId = userService.getUserSelectedClassroomId(authentication);
        Classroom classroom = classroomRepository.findById(selectedClassroomId).orElse(null);
        if (classroom != null) {
            List<Student> studentsList = classroom.getStudents();
            List<Student> newStudentsList = new ArrayList<>();
            for (Student student : studentsList) {
                Comparator<Meeting> meetingDateComparator = Comparator.comparing(Meeting::getDate);
                List<Meeting> allMeetings = student.getMeetings();
                Meeting readingMeeting = allMeetings.stream().filter(m -> m.getSubject().equals("Reading")).max(meetingDateComparator).orElse(new Meeting());
                Meeting writingMeeting = allMeetings.stream().filter(m -> m.getSubject().equals("Writing")).max(meetingDateComparator).orElse(new Meeting());
                student.setMeetings(List.of(readingMeeting, writingMeeting));
                newStudentsList.add(student);
            }
            return newStudentsList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getStudentsWithLastWritingMeetingByClassroom(Authentication authentication) {
        long selectedClassroomId = userService.getUserSelectedClassroomId(authentication);
        Classroom classroom = classroomRepository.findById(selectedClassroomId).orElse(null);
        if (classroom != null) {
            List<Student> studentsList = classroom.getStudents();
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
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getStudentsWithLastReadingMeetingByClassroom(Authentication authentication) {
        long selectedClassroomId = userService.getUserSelectedClassroomId(authentication);
        Classroom classroom = classroomRepository.findById(selectedClassroomId).orElse(null);
        if (classroom != null) {
            List<Student> studentsList = classroom.getStudents();
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
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getAllStudentsBySelectedClassroom(Authentication authentication) {
        long selectedClassroomId = userService.getUserSelectedClassroomId(authentication);
        Classroom classroom = classroomRepository.findById(selectedClassroomId).orElse(null);
        if (classroom != null) {
            return classroom.getStudents();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getAllStudentsByClassroomId(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).orElse(null);
        if (classroom != null) {
            return classroom.getStudents();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getStudentsByIds(String[] ids) {
        List<Long> idsList = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toList());
        return studentRepository.findStudentsByIdIsIn(idsList);
    }

}