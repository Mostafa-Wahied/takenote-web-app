package com.mostafawahied.takenotewebapp.controller;

import com.google.gson.Gson;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.service.MeetingService;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingRepository meetingRepository;

    //    viewHomePage
    @GetMapping("/")
    public String homePage(Model model) {
        //        for navigation active state
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/notebook/students")
    public String viewAllStudentsPage(Model model, Student student, Authentication authentication) throws Exception {
        model.addAttribute("listStudents", studentService.getAllStudents(authentication));
        //        for navigation active state
        model.addAttribute("activePage", "studentsPage");
        //        getting students with last meeting
        model.addAttribute("studentsWithLastMeeting", studentService.getStudentsWithLastMeeting(authentication));
        return "students";
    }

    //display student by ID using a path variable
    @GetMapping("/notebook/student/{id}")
    public String viewStudentById(@PathVariable(value = "id") long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("studentId", id);
        model.addAttribute("meetings", student.getMeetings());
        model.addAttribute("meetingCount", student.getMeetings().size());
        return "student";
    }

    //    display all students for reading
    @GetMapping("/notebook/reading")
    public String viewReadingStudentsPage(Model model, Authentication authentication) throws Exception {
        model.addAttribute("listStudents", studentService.getAllStudents(authentication));
        //        for navigation active state
        model.addAttribute("activePage", "notebookReadingPage");
        // getting students with last meeting
        model.addAttribute("studentsWithLastMeetingReading", studentService.getStudentsWithLastMeetingReading(authentication));
        return "notebook_reading";
    }


    //    display all students for writing
    @GetMapping("/notebook/writing")
    public String viewWritingStudentsPage(Model model, Authentication authentication) throws Exception {
        model.addAttribute("listStudents", studentService.getAllStudents(authentication));
        //        for navigation active state
        model.addAttribute("activePage", "notebookWritingPage");
        //        getting students with last meeting
        model.addAttribute("studentsWithLastMeetingWriting", studentService.getStudentsWithLastMeetingWriting(authentication));
        return "notebook_writing";
    }

    // a page to add a new student when Add Student button clicked
    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "new_student";
    }

    // save student to database
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student, Authentication authentication) {
        studentService.saveStudent(student, authentication);
        return "redirect:/showNewStudentForm?success";
    }

    //    update student info
    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable(value = "id") long id, Model model) {
//        get employee from the service
        Student student = studentService.getStudentById(id);
//        set student as a model attr to pre-populate the form
        model.addAttribute("student", student);
        return "update_student";
    }

    @PostMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id,
                                @RequestParam(name = "confirm", required = false, defaultValue = "false") boolean confirm) {
        if (confirm) {
            // call delete student method
            studentService.deleteStudentById(id);
        }
        return "redirect:/notebook/students";
    }


    //    view reading conference
    @GetMapping("/notetaker/reading")
    public String viewNotetakerReadingPage(Model model) {
        //        for navigation active state
        model.addAttribute("activePage", "notetakerReading");
        return "notetaker_reading";
    }

    //    view writing conference
    @GetMapping("/notetaker/writing")
    public String viewNotetakerWritingPage(Model model) {
        //        for navigation active state
        model.addAttribute("activePage", "notetakerWriting");
        return "notetaker_writing";
    }


    //    view about page
    @GetMapping("/about")
    public String viewAboutPage(Model model) {
//        for navigation active state
        model.addAttribute("activePage", "about");
        return "about";
    }

    // Reading Subject Levels progress Line Chart Card
    @ResponseBody
    @GetMapping("/notebook/student/{id}/averageReadingSubjectLevel")
    public String getStudentAverageReadingSubjectLevel(@PathVariable(value = "id") long studentId) {
        Gson gson = new Gson();
        // getAverageReadingSubjectLevel
        List<Map<String, Object>> averageReadingSubjectLevel = meetingService.getStudentAverageSubjectLevelProgress(studentId);
        return gson.toJson(averageReadingSubjectLevel);
    }

}
