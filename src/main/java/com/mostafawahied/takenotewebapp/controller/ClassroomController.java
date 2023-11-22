package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import com.mostafawahied.takenotewebapp.service.ClassroomService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ClassroomController {
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public ClassroomController(ClassroomService classroomService, StudentService studentService, UserService userService) {
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/classrooms")
    public String viewClassroomsPage(Model model, Authentication authentication) throws Exception {
        //        for navigation active state
        model.addAttribute("activePage", "classroomsPage");
        List<Classroom> classrooms = classroomService.getAllClassrooms(authentication);
        model.addAttribute("classrooms", classrooms);
        return "classrooms";
    }

    @GetMapping("/showNewClassroomForm")
    public String showNewClassroomForm(Model model) {
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

    // storing the selected classroom id in the session
    // also using HttpServletRequest to get the referer url to redirect to the page where the form is submitted
    @PostMapping("/select-classroom")
    public String selectClassroom(@RequestParam String selectedClassroomId, HttpServletRequest request, Authentication authentication) {
        User user = userService.getUser(authentication);
        long classroomId = Long.parseLong(selectedClassroomId);
        user.setSelectedClassroomId(classroomId);
        userService.updateUser(user);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @PostMapping("/classroom/delete/{id}")
    public String deleteClassroom(@PathVariable(value = "id") long id,
                                  @RequestParam(name = "confirm", required = false, defaultValue = "false") boolean confirm,
                                  Authentication authentication, Model model) {
        if (confirm) {
            User user = userService.getUser(authentication);
            long selectedClassroomId = user.getSelectedClassroomId();
            if (selectedClassroomId == id) {
                user.setSelectedClassroomId(0);
                userService.updateUser(user);
            }
            if (id != 0) {
                classroomService.deleteClassroomById(id);
            }
        }
        return "redirect:/notebook/students";
    }

}
