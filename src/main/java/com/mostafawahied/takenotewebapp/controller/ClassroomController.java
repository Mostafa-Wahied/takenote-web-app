package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.service.ClassroomService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/classrooms")
    public String viewClassroomsPage(Model model, Authentication authentication) throws Exception {
        //        for navigation active state
        model.addAttribute("activePage", "classroomsPage");
        List<Classroom> classrooms = classroomService.getAllClassrooms(authentication);
        model.addAttribute("classrooms", classrooms);
        return "classrooms";
    }

    @GetMapping("/showNewClassroomForm")
    public String showNewClassroomForm(Model model, Authentication authentication) {
        Classroom classroom = new Classroom();
        model.addAttribute("classroom", classroom);
        return "new_classroom";
    }

    // save classroom to database
    @PostMapping("/saveClassroom")
    public String saveClassroom(@ModelAttribute("classroom") Classroom classroom, Authentication authentication) {
        // save classroom to database
        classroomService.saveClassroom(classroom, authentication);
        return "redirect:/showNewClassroomForm?success";
    }

    // show classroom by id
    @GetMapping("/classroom/{id}")
    public String viewClassroomById(@PathVariable(value = "id") long id, Model model, Authentication authentication) {
        Classroom classroom = classroomService.getClassroomById(id);
        model.addAttribute("classroom", classroom);
        model.addAttribute("classroomId", id);
        //        getting students with last meeting
        model.addAttribute("studentsWithLastMeeting", studentService.getStudentsWithLastMeeting(authentication));
        return "classroom";
    }
}
