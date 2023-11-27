package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class MeetingController {
    private final StudentService studentService;
    private final MeetingService meetingService;
    public MeetingController(StudentService studentService, MeetingService meetingService) {
        this.studentService = studentService;
        this.meetingService = meetingService;
    }

    // View 1:1 reading form
    @GetMapping("/notetaker/reading/1on1")
    public String show1on1ReadingForm(Model model, Authentication authentication) {
        // Get all students
        model.addAttribute("listStudents", studentService.getAllStudentsBySelectedClassroom(authentication));
        return "1on1_reading_form";
    }

    // Save 1:1 reading conference
    @PostMapping("/save1on1ReadingMeeting")
    public String saveReading1on1Meeting(@ModelAttribute("meetingObject") Meeting meeting,
                                         @RequestParam(name = "id") String id,
                                         @RequestParam(name = "readingLevelValue") Character readingLevel,
                                         @RequestParam(name = "strengthValue") String strength,
                                         @RequestParam(name = "teachingPointValue") String teachingPoint,
                                         @RequestParam(name = "nextStepValue") String nextStep, Model model) {
        Meeting createdMeeting = meetingService.saveReading1on1Meeting(meeting, id, readingLevel, strength, teachingPoint, nextStep);
        model.addAttribute("meeting", createdMeeting);
        return "redirect:/notebook/students";
    }

    // View 1:1 writing form
    @GetMapping("/notetaker/writing/1on1")
    public String show1on1WritingForm(Model model, Authentication authentication) {
        model.addAttribute("listStudents", studentService.getAllStudentsBySelectedClassroom(authentication));
        return "1on1_writing_form";
    }

    // Save 1:1 Writing conference
    @PostMapping("/save1on1WritingMeeting")
    public String saveWriting1on1Meeting(@ModelAttribute("meetingObject") Meeting meeting,
                                         @RequestParam(name = "id") String id,
                                         @RequestParam(name = "strengthValue") String strength,
                                         @RequestParam(name = "teachingPointValue") String teachingPoint,
                                         @RequestParam(name = "nextStepValue") String nextStep, Model model) {
        Meeting CreatedMeeting = meetingService.saveWriting1on1Meeting(meeting, id, strength, teachingPoint, nextStep);
        model.addAttribute("meeting", CreatedMeeting);
        return "redirect:/notebook/students";
    }

    // View guided reading form
    @GetMapping("/notetaker/reading/guided_reading")
    public String showGuidedReadingForm(Model model, Authentication authentication) {
        // get all students
        model.addAttribute("listStudents", studentService.getAllStudentsBySelectedClassroom(authentication));
        return "guided_reading_form";
    }

    // Save multiple students - guided reading conference
    @PostMapping("/saveGuidedReadingMeeting")
    public String saveMultipleGuidedReadingMeeting(@RequestParam(name = "id") String[] ids,
                                                   @RequestParam(name = "date") Date date,
                                                   @RequestParam(name = "readingLevelValue") Character readingLevel,
                                                   @RequestParam(name = "teachingPointValue") String teachingPoint,
                                                   Model model) {
        List<Meeting> createdMeetings = meetingService.saveMultipleGuidedReadingMeetings(ids, date, readingLevel, teachingPoint, model);
        List<Student> students = studentService.getStudentsByIds(ids);
        addAttributesToModel(ids, students, createdMeetings, model);
        return "follow_up_form";
    }

    // View reading strategy group form
    @GetMapping("/notetaker/reading/strategy_group")
    public String showStrategyGroupReadingForm(Model model, Authentication authentication) {
        // get all students
        model.addAttribute("listStudents", studentService.getAllStudentsBySelectedClassroom(authentication));
        return "strategy_group_reading_form";
    }

    // Save multiple students - reading strategy group conference
    @PostMapping("/saveStrategyReadingMeeting")
    public String saveMultipleStrategyReadingMeeting(@RequestParam(name = "id") String[] ids,
                                                     @RequestParam(name = "date") Date date,
                                                     @RequestParam(name = "teachingPointValue") String teachingPoint,
                                                     Model model) {
        List<Meeting> createdMeetings = meetingService.saveMultipleStrategyReadingMeetings(ids, date, teachingPoint, model);
        List<Student> students = studentService.getStudentsByIds(ids);
        addAttributesToModel(ids, students, createdMeetings, model);
        return "follow_up_form";
    }

    // View writing strategy group form
    @GetMapping("/notetaker/writing/strategy_group")
    public String showStrategyGroupWritingForm(Model model, Authentication authentication) {
        // get all students
        model.addAttribute("listStudents", studentService.getAllStudentsBySelectedClassroom(authentication));
        return "strategy_group_writing_form";
    }

    // Save strategy group writing conference
    @PostMapping("/saveStrategyGroupWritingMeeting")
    public String saveMultipleStrategyGroupWritingMeeting(@RequestParam(name = "id") String[] ids,
                                                          @RequestParam(name = "date") Date date,
                                                          @RequestParam(name = "teachingPointValue") String teachingPoint,
                                                          Model model) {
        List<Meeting> createdMeetings = meetingService.saveMultipleStrategyWritingMeetings(ids, date, teachingPoint, model);
        List<Student> students = studentService.getStudentsByIds(ids);
        addAttributesToModel(ids, students, createdMeetings, model);
        return "follow_up_form";
    }

    // Get the previous meeting and save update it with Strength and Next step
    @PostMapping("/saveFollowUpMeeting")
    public String saveFollowUpGuidedReadingMeeting(Model model, @RequestParam(name = "meetingId") String[] ids,
                                                   @RequestParam(name = "strengthValues") List<String> strengthList,
                                                   @RequestParam(name = "nextStepValues") List<String> nextStepsList) {
        meetingService.saveFollowUpMeetings(ids, strengthList, nextStepsList, model);
        return "redirect:/notebook/students";
    }

    // Helper method to add attributes to the model
    private void addAttributesToModel(String[] ids, List<Student> students, List<Meeting> createdMeetings, Model model) {
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", createdMeetings);
    }
}
