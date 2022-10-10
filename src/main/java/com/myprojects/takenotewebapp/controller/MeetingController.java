package com.myprojects.takenotewebapp.controller;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.Student;
import com.myprojects.takenotewebapp.repository.MeetingRepository;
import com.myprojects.takenotewebapp.repository.StudentRepository;
import com.myprojects.takenotewebapp.service.MeetingService;
import com.myprojects.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MeetingController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingService meetingService;

    // save 1:1 reading conference
    @PostMapping("/save1on1ReadingMeeting")
    public String saveReading1on1Meeting(@ModelAttribute("meetingObject") Meeting meeting,
                                         @RequestParam(name = "id") String id,
                                         @RequestParam(name = "readingLevelValue") Character readingLevel,
                                         @RequestParam(name = "strengthValue") String strength,
                                         @RequestParam(name = "teachingPointValue") String teachingPoint,
                                         @RequestParam(name = "nextStepValue") String nextStep) {
        System.out.println("*********************** id of student is: " + id);

        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);

        meeting.setStudent(student);
        meeting.setSubject("Reading");
        meeting.setType("1:1");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    // save guided reading conference
    @PostMapping("/saveGuidedReadingMeeting")
    public String saveGuidedReadingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                           @RequestParam(name = "id") String id,
                                           @RequestParam(name = "readingLevelValue") Character readingLevel,
                                           @RequestParam(name = "strengthValue") String strength,
                                           @RequestParam(name = "teachingPointValue") String teachingPoint,
                                           @RequestParam(name = "nextStepValue") String nextStep) {
        System.out.println("*********************** name of student is: " + id);

        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);

        meeting.setStudent(student);
        meeting.setSubject("Reading");
        meeting.setType("guided reading");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    // save strategy group reading conference
    @PostMapping("/saveStrategyGroupReadingMeeting")
    public String saveStrategyGroupReadingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                           @RequestParam(name = "id") String id,
                                           @RequestParam(name = "readingLevelValue") Character readingLevel,
                                           @RequestParam(name = "strengthValue") String strength,
                                           @RequestParam(name = "teachingPointValue") String teachingPoint,
                                           @RequestParam(name = "nextStepValue") String nextStep) {
        System.out.println("*********************** name of student is: " + id);

        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);

        meeting.setStudent(student);
        meeting.setSubject("reading");
        meeting.setType("strategy group");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    // save 1:1 Writing conference
    @PostMapping("/save1on1WritingMeeting")
    public String saveWriting1on1Meeting(@ModelAttribute("meetingObject") Meeting meeting,
                                         @RequestParam(name = "id") String id,
                                         @RequestParam(name = "strengthValue") String strength,
                                         @RequestParam(name = "teachingPointValue") String teachingPoint,
                                         @RequestParam(name = "nextStepValue") String nextStep) {
        System.out.println("*********************** name of student is: " + id);

        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);

        meeting.setStudent(student);
        meeting.setSubject("Writing");
        meeting.setType("1:1");
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    // save strategy group writing conference
    @PostMapping("/saveStrategyGroupWritingMeeting")
    public String saveStrategyGroupWritingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                                  @RequestParam(name = "id") String id,
                                                  @RequestParam(name = "readingLevelValue") Character readingLevel,
                                                  @RequestParam(name = "strengthValue") String strength,
                                                  @RequestParam(name = "teachingPointValue") String teachingPoint,
                                                  @RequestParam(name = "nextStepValue") String nextStep) {
        System.out.println("*********************** name of student is: " + id);

        Integer theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);

        meeting.setStudent(student);
        meeting.setSubject("writing");
        meeting.setType("strategy group");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

}
