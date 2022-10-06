package com.myprojects.takenotewebapp.controller;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.MeetingId;
import com.myprojects.takenotewebapp.model.Student;
import com.myprojects.takenotewebapp.repository.MeetingRepository;
import com.myprojects.takenotewebapp.repository.StudentRepository;
import com.myprojects.takenotewebapp.service.MeetingService;
import com.myprojects.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingService meetingService;

    //    viewHomePage
    @GetMapping("/")
    public String homePage(Model model) {
        //        for navigation active state
        model.addAttribute("activePage", "home");
        return "index";
    }

    //    display all students
    @GetMapping("/notebook/students")
    public String viewAllStudentsPage(Model model, Student student) {
        model.addAttribute("listStudents", studentService.getAllStudents());
        //        for navigation active state
        model.addAttribute("activePage", "studentsPage");
        return "students";
    }

    //    display all students for reading
    @GetMapping("/notebook/reading")
    public String viewReadingStudentsPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        //        for navigation active state
        model.addAttribute("activePage", "notebookReadingPage");
        return "notebook_reading";
    }

    //    display all students for writing
    @GetMapping("/notebook/writing")
    public String viewWritingStudentsPage(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        //        for navigation active state
        model.addAttribute("activePage", "notebookWritingPage");
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
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/notebook/students";
    }

    @PostMapping("/saveMeeting")
    public String saveMeeting(@ModelAttribute("meeting") Meeting meeting) {
        meetingService.saveMeeting(meeting);
        return "redirect:/notebook/students";
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

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id, Model model) {
//        for navigation active state
        model.addAttribute("activePage", "studentsPage");

//        call delete student method
        studentService.deleteStudentById(id);
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


    //    view 1:1 reading form
    @GetMapping("/notetaker/reading/1on1")
    public String show1on1ReadingForm(Model model) {
//        get all students
        model.addAttribute("listStudents", studentService.getAllStudents());
        model.addAttribute("meetings", meetingService.getAllMeetings());
        return "1on1ReadingForm";
    }

    //    view 1:1 reading form
    @GetMapping("/notetaker/writing/1on1")
    public String show1on1WritingForm(Model model) {
//        get all students
        model.addAttribute("students", studentService.getAllStudents());
        return "1on1WritingForm";
    }

    @GetMapping
    public String findStudentByName(Model model, String firstName) {
        model.addAttribute("student", studentService.getStudentByFirstName(firstName));
        return "redirect:/notebook/students";
    }

    //    view about page
    @GetMapping("/about")
    public String viewAboutPage(Model model) {
//        for navigation active state
        model.addAttribute("activePage", "about");
        return "about";
    }

}
