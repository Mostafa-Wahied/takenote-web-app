package com.mostafawahied.takenotewebapp.controller;

import com.google.gson.Gson;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;

@Controller
public class DashboardController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingService meetingService;


    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        List<Student> students = studentService.getAllStudents(principal);
        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
        model.addAttribute("principal", principal.getName());
        // setting active page for navbar
        model.addAttribute("activePage", "dashboard");

        return "dashboard";
    }

    // Reading Meeting Count By Student And Type Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getReadingMeetingCountByStudentAndType")
    public String getReadingMeetingCountByStudentAndType(Principal principal) {
        Gson gson = new Gson();
        // getMeetingCountByStudentAndType
        Map<String, Map<String, Integer>> meetingCountByStudentAndType = meetingService.getReadingMeetingCountByStudentAndType(principal);
        return gson.toJson(meetingCountByStudentAndType);
    }

    // Writing Meeting Count By Student And Type Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getWritingMeetingCountByStudentAndType")
    public String getWritingMeetingCountByStudentAndType(Principal principal) {
        Gson gson = new Gson();
        // getMeetingCountByStudentAndType
        Map<String, Map<String, Integer>> meetingCountByStudentAndType = meetingService.getWritingMeetingCountByStudentAndType(principal);
        return gson.toJson(meetingCountByStudentAndType);
    }

    // Meeting Count By Type Pie Chart
    @ResponseBody
    @GetMapping("/dashboard/getMeetingCountByType")
    public String getMeetingCountByType() {
        Gson gson = new Gson();
        // getMeetingCountByType
        List<Map<String, Object>> meetingCountByType = meetingService.getMeetingCountByType();
        return gson.toJson(meetingCountByType);
    }

    // Meeting Count By Student Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getWritingMeetingsCountByStudent")
    public String getWritingMeetingsCountByStudent(Principal principal) {
        Gson gson = new Gson();
        // getMeetingCountByStudent
        List<Map<String, Object>> meetingCountByStudent = meetingService.getWritingMeetingCountByStudent(principal);
        return gson.toJson(meetingCountByStudent);
    }

    // Reading Meeting Count By Student Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getReadingMeetingsCountByStudent")
    public String getReadingMeetingCountByStudent(Principal principal) {
        Gson gson = new Gson();
        // getReadingMeetingCountByStudent
        List<Map<String, Object>> readingMeetingCountByStudent = meetingService.getReadingMeetingCountByStudent(principal);
        return gson.toJson(readingMeetingCountByStudent);
    }

}
