package com.myprojects.takenotewebapp;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.Student;
import com.myprojects.takenotewebapp.repository.MeetingRepository;
import com.myprojects.takenotewebapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class TakenoteWebAppApplication implements CommandLineRunner {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    public static void main(String[] args) {
        SpringApplication.run(TakenoteWebAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Student student = new Student();
//        Meeting meeting = new Meeting();
//
//        student.setFirstName("firstname4");
//        student.setLastName("lastname4");
//        meeting.setStudent(student);
////        meeting.setMeetingId(new MeetingId(LocalDate.of(2022, 8, 25), "reading", "strategy group"));
//        meeting.setDate(LocalDate.of(2022, 8, 25));
//        meeting.setSubject("reading");
//        meeting.setType("strategy group");
//        meeting.setStrength("pronouncing letters");
//        meeting.setTeachingPoint("reading aloud");
//        meeting.setSubjectLevel('d');
//        meeting.setTeachingPoint("reading words");
//        meeting.setNextStep("reading sentences");
//
//        studentRepository.save(student);
//        meetingRepository.save(meeting);
    }
}
