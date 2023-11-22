package com.mostafawahied.takenotewebapp.controller;

import com.google.gson.Gson;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import com.mostafawahied.takenotewebapp.service.ClassroomService;
import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DashboardController {
    private final StudentService studentService;
    private final MeetingService meetingService;
    private final UserService userService;

    @Autowired
    public DashboardController(StudentService studentService, MeetingService meetingService, UserService userService) {
        this.studentService = studentService;
        this.meetingService = meetingService;
        this.userService = userService;
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) throws Exception {
        model.addAttribute("principal", authentication.getName());
        // setting active page for navbar
        model.addAttribute("activePage", "dashboard");

        // inject the average reading level for all meetings for the current user
//        Float averageReadingLevel = meetingService.getAverageReadingLevel(authentication);
//        model.addAttribute("averageReadingLevel", averageReadingLevel);
        // for classrooms
        addDashboardAttributes(model, authentication);
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

    // helper method to get the user's students and classrooms and add them to the model
    public void addDashboardAttributes(Model model, Authentication authentication) throws Exception {
        long selectedClassroomId = userService.getUserSelectedClassroomId(authentication);
        List<Student> students = studentService.getStudentsWithLastWritingMeetingByClassroom(authentication);
        model.addAttribute("students", students);
        model.addAttribute("studentsCount", studentService.getAllStudentsBySelectedClassroom(authentication).size());
        model.addAttribute("meetingsCount", meetingService.getMeetingsByClassroom(selectedClassroomId));
        Float averageReadingLevel = meetingService.getAverageReadingLevelBySelectedClassroomId(selectedClassroomId);
        model.addAttribute("averageReadingLevel", averageReadingLevel);
    }
}
