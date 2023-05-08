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

        Gson gson = new Gson();
        // getMeetingCountByStudentAndType
        Map<String, Map<String, Integer>> meetingCountByStudentAndType = meetingService.getMeetingCountByStudentAndType();
        String meetingCountByStudentAndTypeJson = gson.toJson(meetingCountByStudentAndType);
        model.addAttribute("meetingCountByStudentAndTypeJson", meetingCountByStudentAndTypeJson);

        return "dashboard";
    }


}
