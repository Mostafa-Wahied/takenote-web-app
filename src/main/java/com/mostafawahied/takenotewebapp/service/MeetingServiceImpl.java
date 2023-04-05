package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingById(long id) {
        Optional<Meeting> optional = meetingRepository.findById(id);

        Meeting meeting = null;
        if (optional.isPresent()) {
            meeting = optional.get();
        } else {
            throw new RuntimeException(("Meeting not found for id: " + id));
        }
        return meeting;
    }

    @Override
    public void saveReading1on1Meeting(Meeting meeting, String id, Character readingLevel, String strength, String teachingPoint, String nextStep, Model model) {
        int theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        meeting.setStudent(student);
        meeting.setSubject("Reading");
        meeting.setType("1:1 - Reading");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        this.saveMeeting(meeting);
        model.addAttribute("meetingObject", meeting);
    }

    @Override
    public void saveWriting1on1Meeting(Meeting meeting, String id, String strength, String teachingPoint, String nextStep, Model model) {
        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);
        meeting.setStudent(student);
        meeting.setSubject("Writing");
        meeting.setType("1:1 - Writing");
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        this.saveMeeting(meeting);
        model.addAttribute("meetingObject", meeting);
    }

    public void saveMultipleGuidedReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Guided Reading");
            newMeeting.setSubjectLevel(readingLevel);
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveMultipleStrategyReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Strategy Group - Reading");
            newMeeting.setSubjectLevel(readingLevel);
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Writing");
            newMeeting.setType("Strategy Group - Writing");
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveFollowUpMeetings(String[] ids, List<String> strengthList, List<String> nextStepsList) {
        // Iterate over the students and create a Meeting object for each one
        for (int i = 0; i < ids.length; i++) {
            int theId = Integer.parseInt(ids[i]);
            // Retrieve the existing Meeting object by its ID
            Meeting followUpMeeting = this.getMeetingById(theId);
            followUpMeeting.setStrength(strengthList.get(i));
            followUpMeeting.setNextStep(nextStepsList.get(i));
            this.saveMeeting(followUpMeeting);
        }
    }
}
