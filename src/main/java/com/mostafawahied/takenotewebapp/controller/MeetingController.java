package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;

@Controller
public class MeetingController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingRepository meetingRepository;


    //    view 1:1 reading form
    @GetMapping("/notetaker/reading/1on1")
    public String show1on1ReadingForm(Model model, Principal principal) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents(principal));
//        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "1on1_reading_form";
    }

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
        meeting.setType("1:1 - Reading");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    //    view guided reading form
    @GetMapping("/notetaker/reading/guided_reading")
    public String showGuidedReadingForm(Model model, Principal principal) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents(principal));
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "guided_reading_form";
    }

    // save multiple students - guided reading conference
    @PostMapping("/saveGuidedReadingMeeting")
    public String saveMultipleGuidedReadingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                                   @RequestParam(name = "id") String id,
                                                   @RequestParam(name = "date") Date date,
                                                   @RequestParam(name = "readingLevelValue") Character readingLevel,
                                                   @RequestParam(name = "teachingPointValue") String teachingPoint) {
        System.out.println("*********************** name of student is: " + id);
        studentService.saveMultipleGuidedReadingStudents(meeting, id, date, readingLevel, teachingPoint);
        return "redirect:/notebook/students";
    }

    //    view reading strategy group form
    @GetMapping("/notetaker/reading/strategy_group")
    public String showStrategyGroupReadingForm(Model model, Principal principal) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents(principal));
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "strategy_group_reading_form";
    }

    // save multiple students - reading strategy group conference
    @PostMapping("/saveStrategyReadingMeeting")
    public String saveMultipleStrategyReadingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                                     @RequestParam(name = "id") String id,
                                                     @RequestParam(name = "date") Date date,
                                                     @RequestParam(name = "readingLevelValue") Character readingLevel,
                                                     @RequestParam(name = "teachingPointValue") String teachingPoint) {
        System.out.println("*********************** name of student is: " + id);
        studentService.saveMultipleStrategyReadingStudents(meeting, id, date, readingLevel, teachingPoint);
        return "redirect:/notebook/students";
    }

    //    view 1:1 writing form
    @GetMapping("/notetaker/writing/1on1")
    public String show1on1WritingForm(Model model, Principal principal) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents(principal));
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "1on1_writing_form";
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
        meeting.setType("1:1 - Writing");
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
    }

    //    view writing strategy group form
    @GetMapping("/notetaker/writing/strategy_group")
    public String showStrategyGroupWritingForm(Model model, Principal principal) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents(principal));
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "strategy_group_writing_form";
    }

    // save strategy group writing conference
    @PostMapping("/saveStrategyGroupWritingMeeting")
    public String saveStrategyGroupWritingMeeting(@ModelAttribute("meetingObject") Meeting meeting,
                                                  @RequestParam(name = "id") String id,
                                                  @RequestParam(name = "date") Date date,
                                                  @RequestParam(name = "readingLevelValue") Character readingLevel,
                                                  @RequestParam(name = "teachingPointValue") String teachingPoint) {
        System.out.println("*********************** name of student is: " + id);
        studentService.saveMultipleStrategyWritingStudents(meeting, id, date, readingLevel, teachingPoint);
        return "redirect:/notebook/students";
    }
}
