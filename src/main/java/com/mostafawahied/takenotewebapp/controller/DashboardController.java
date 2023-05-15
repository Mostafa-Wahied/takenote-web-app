package com.mostafawahied.takenotewebapp.controller;

import com.google.gson.Gson;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String dashboard(Model model, Authentication authentication) {
        List<Student> students = studentService.getAllStudents(authentication);
        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
        model.addAttribute("principal", authentication.getName());
        // meetings count for the current user
        model.addAttribute("meetingsCount", meetingService.getMeetingCount(authentication));
        // students count for the current user
        model.addAttribute("studentsCount", studentService.getAllStudents(authentication).size());
        // setting active page for navbar
        model.addAttribute("activePage", "dashboard");

        // inject the average reading level for all meetings for the current user
        Float averageReadingLevel = meetingService.getAverageReadingLevel(authentication);
        model.addAttribute("averageReadingLevel", averageReadingLevel);
        return "dashboard";
    }

    // Reading Meeting Count By Student And Type Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getReadingMeetingCountByStudentAndType")
    public String getReadingMeetingCountByStudentAndType(Authentication authentication) {
        Gson gson = new Gson();
        // getMeetingCountByStudentAndType
        Map<String, Map<String, Integer>> meetingCountByStudentAndType = meetingService.getReadingMeetingCountByStudentAndType(authentication);
        return gson.toJson(meetingCountByStudentAndType);
    }

    // Writing Meeting Count By Student And Type Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getWritingMeetingCountByStudentAndType")
    public String getWritingMeetingCountByStudentAndType(Authentication authentication) {
        Gson gson = new Gson();
        // getMeetingCountByStudentAndType
        Map<String, Map<String, Integer>> meetingCountByStudentAndType = meetingService.getWritingMeetingCountByStudentAndType(authentication);
        return gson.toJson(meetingCountByStudentAndType);
    }

    // Meeting Count By Type Pie Chart
    @ResponseBody
    @GetMapping("/dashboard/getMeetingCountByType")
    public String getMeetingCountByType(Authentication authentication) {
        Gson gson = new Gson();
        // getMeetingCountByType
        List<Map<String, Object>> meetingCountByType = meetingService.getMeetingCountByType(authentication);
        return gson.toJson(meetingCountByType);
    }

    // Writing Meeting Count By Student Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getWritingMeetingsCountBySubject")
    public String getWritingMeetingsCountByStudent(Authentication authentication) {
        Gson gson = new Gson();
        // getMeetingCountByStudent
        List<Map<String, Object>> meetingCountByStudent = meetingService.getWritingMeetingCountByStudentBySubject(authentication);
        return gson.toJson(meetingCountByStudent);
    }

    // Reading Meeting Count By Student Bar Chart
    @ResponseBody
    @GetMapping("/dashboard/getReadingMeetingsCountBySubject")
    public String getReadingMeetingCountByStudent(Authentication authentication) {
        Gson gson = new Gson();
        // getReadingMeetingCountByStudent
        List<Map<String, Object>> readingMeetingCountByStudent = meetingService.getReadingMeetingCountByStudentBySubject(authentication);
        return gson.toJson(readingMeetingCountByStudent);
    }

    // Reading Subject Levels progress Line Chart Card
    @ResponseBody
    @GetMapping("/dashboard/getAverageReadingSubjectLevel")
    public String getAverageReadingSubjectLevel(Authentication authentication) {
        Gson gson = new Gson();
        // getAverageReadingSubjectLevel
        List<Map<String, Object>> averageReadingSubjectLevel = meetingService.getAverageSubjectLevelProgress(authentication);
        return gson.toJson(averageReadingSubjectLevel);
    }

    // Average reading level for all meetings for the logged in user
//    @ResponseBody
//    @GetMapping("/dashboard/getAverageReadingLevel")
//    public String getAverageReadingLevel(Principal principal) {
//        Gson gson = new Gson();
//        // getAverageReadingLevel
//        float averageReadingLevel = meetingService.getAverageReadingLevel(principal);
//        return gson.toJson(averageReadingLevel);
//    }
}
