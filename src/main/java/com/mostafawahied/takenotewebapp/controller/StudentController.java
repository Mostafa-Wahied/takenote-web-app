package com.mostafawahied.takenotewebapp.controller;

import com.google.gson.Gson;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import com.mostafawahied.takenotewebapp.service.*;
import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final MeetingService meetingService;
    private final UserRepository userRepository;
    private final FileImportService fileImportService;
    private final ReadingLevelService readingLevelService;

    public StudentController(ClassroomService classroomService, StudentService studentService, MeetingService meetingService, UserRepository userRepository, FileImportService fileImportService, ReadingLevelService readingLevelService) {
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.meetingService = meetingService;
        this.userRepository = userRepository;
        this.fileImportService = fileImportService;
        this.readingLevelService = readingLevelService;
    }


    @GetMapping("/notebook/students")
    public String viewAllStudentsPage(Model model, Student student, Authentication authentication) throws Exception {
        model.addAttribute("activePage", "studentsPage");
        addAttributesForStudents(model, authentication, "all");
        return "students";
    }

    //display student by ID using a path variable
    @GetMapping("/notebook/student/{id}")
    public String viewStudentById(@PathVariable(value = "id") long id, Model model) throws Exception {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("studentId", id);
        model.addAttribute("meetings", student.getMeetings());
        model.addAttribute("meetingCount", student.getMeetings().size());
        model.addAttribute("currentReadingLevel", student.getCurrentReadingLevel());
        return "student";
    }

    //    display all students for reading
    @GetMapping("/notebook/reading")
    public String viewReadingStudentsPage(Model model, Authentication authentication) throws Exception {
        model.addAttribute("activePage", "notebookReadingPage");
        addAttributesForStudents(model, authentication, "reading");
        return "notebook_reading";
    }


    //    display all students for writing
    @GetMapping("/notebook/writing")
    public String viewWritingStudentsPage(Model model, Authentication authentication) throws Exception {
        model.addAttribute("activePage", "notebookWritingPage");
        addAttributesForStudents(model, authentication, "writing");
        return "notebook_writing";
    }

    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(@RequestParam(value = "classroomId", required = false) Long classroomId,
                                     Model model, Authentication authentication) throws Exception {
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("classrooms", classroomService.getAllClassrooms(authentication));
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("classroomIds", classroomService.getAllClassroomIds(authentication));
        String userEmail = classroomService.getUserEmailFromAuthentication(authentication);
        model.addAttribute("selectedClassroomId", userRepository.findUserByEmail(userEmail).getSelectedClassroomId());
        return "new_student";
    }

    // save student to database
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student,
                              Authentication authentication) throws Exception {
        String userEmail = classroomService.getUserEmailFromAuthentication(authentication);
        User user = userRepository.findUserByEmail(userEmail);
        Long classroomId = user.getSelectedClassroomId();
        studentService.saveStudent(student, classroomId, authentication);
        return "redirect:/showNewStudentForm?success";
    }

    // import students from a csv or excel file
    @PostMapping("/importStudents")
    public String importStudents(@RequestParam("file") MultipartFile file,
                                 @RequestParam(name = "hasHeader", defaultValue = "false") boolean hasHeader,
                                 RedirectAttributes redirectAttributes, Authentication authentication) throws Exception {
        String userEmail = classroomService.getUserEmailFromAuthentication(authentication);
        User user = userRepository.findUserByEmail(userEmail);
        Long classroomId = user.getSelectedClassroomId();
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload");
            return "redirect:/showNewStudentForm?error";
        }
        if (!isFileFormatValid(file)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid file format. Please upload a CSV or Excel file.");
            return "redirect:/showNewStudentForm?error";
        }
        try {
            List<Student> students = fileImportService.importStudents(file, hasHeader);
            for (Student student : students) {
                studentService.saveStudent(student, classroomId, authentication);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error importing students: " + e.getMessage());
            return "redirect:/showNewStudentForm?error";
        }
        return "redirect:/showNewStudentForm?importSuccess";
    }

    //    helper method to validate the file format
    private boolean isFileFormatValid(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtension.equals("csv") || fileExtension.equals("xlsx") || fileExtension.equals("xls");
    }

    //    update student info
    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable(value = "id") long id, Model model) throws Exception {
//        get employee from the service
        Student student = studentService.getStudentById(id);
//        set student as a model attr to pre-populate the form
        model.addAttribute("student", student);
        return "update_student";
    }

    @PostMapping("/deleteStudent/{id}")
    public String deleteStudent(
            @PathVariable(value = "id") long id,
            @RequestParam(name = "confirm", required = false, defaultValue = "false") boolean confirm) throws Exception {
        if (confirm) {
            studentService.deleteStudentById(id);
        }
        return "redirect:/notebook/students";
    }

    //    view reading conference
    @GetMapping("/notetaker/reading")
    public String viewNotetakerReadingPage(Model model) throws Exception {
        //        for navigation active state
        model.addAttribute("activePage", "notetakerReading");
        return "notetaker_reading";
    }

    //    view writing conference
    @GetMapping("/notetaker/writing")
    public String viewNotetakerWritingPage(Model model) throws Exception {
        //        for navigation active state
        model.addAttribute("activePage", "notetakerWriting");
        return "notetaker_writing";
    }


    // Reading Subject Levels progress Line Chart Card
    @ResponseBody
    @GetMapping("/notebook/student/{id}/averageReadingSubjectLevel")
    public String getStudentAverageReadingSubjectLevel(@PathVariable(value = "id") long studentId) throws Exception {
        Gson gson = new Gson();
        // getAverageReadingSubjectLevel
        List<Map<String, Object>> averageReadingSubjectLevel = meetingService.getStudentAverageSubjectLevelProgress(studentId);
        return gson.toJson(averageReadingSubjectLevel);
    }


    // helper method to get the user's students and classrooms and add them to the model
    private void addAttributesForStudents(Model model, Authentication authentication, String meetingType) throws Exception {
        List<Student> students;
        if (meetingType.equals("reading")) {
            students = studentService.getStudentsWithLastReadingMeetingByClassroom(authentication);
        } else if (meetingType.equals("writing")) {
            students = studentService.getStudentsWithLastWritingMeetingByClassroom(authentication);
        } else {
            students = studentService.getStudentsWithLastAllMeetingByClassroom(authentication);
        }

        Map<Long, Character> readingLevels = new HashMap<>();
        for (Student student : students) {
            Character latestReadingLevel = readingLevelService.getLatestReadingLevel(student);
            if (latestReadingLevel != null) {
                readingLevels.put(student.getId(), latestReadingLevel);
            }
        }

        model.addAttribute("alphabetList", readingLevelService.alphabetList());
        model.addAttribute("readingLevels", readingLevels);
        model.addAttribute("students", students);
    }

    @PostMapping("/updateReadingLevel")
    @ResponseBody
    public Map<String, Object> updateReadingLevel(@RequestParam("studentId") long studentId,
                                                  @RequestParam("newLevel") Character newLevel) {
        readingLevelService.updateReadingLevel(studentId, newLevel);
        Map<String, Object> response = new HashMap<>();
        response.put("newReadingLevel", newLevel);
        return response;
    }

}
