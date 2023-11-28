package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.config.CustomOAuth2User;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import com.mostafawahied.takenotewebapp.service.ClassroomService;
import com.mostafawahied.takenotewebapp.service.StudentService;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class MyControllerAdvice {
    private final UserRepository userRepository;
    private final ClassroomService classroomService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    @Autowired
    public MyControllerAdvice(UserRepository userRepository, ClassroomService classroomService, UserService userService) {
        this.userRepository = userRepository;
        this.classroomService = classroomService;
        this.userService = userService;
    }
    // handle SQL syntax errors
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        if (ex.getCause() instanceof SQLSyntaxErrorException) {
            logger.error("SQL Syntax Error occurred triggered by ControllerAdvice at line 46", ex);
            return new ResponseEntity<>("A SQL syntax error has occurred at ControllerAdvice line 47: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // Fallback to generic error handling
        return handleException(ex);
    }
    // handle all other exceptions
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        logger.error("Error occurred at ControllerAdvice at ControllerAdvice line 59", ex);

        return new ResponseEntity<>("Error occurred at ControllerAdvice line 61: " + ex.getMessage() + "<br>" + stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
//        // adding the user's name
//        String name = userService.getLoggedInUserName(authentication);
//        model.addAttribute("name", name);
        // adding the user's profile picture
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;
            // Get the user's profile picture URL from the OAuth2 authentication
            String pictureUrl = oauth2Authentication.getPrincipal().getAttributes().get("picture").toString();
            model.addAttribute("pictureUrl", pictureUrl);

        }
    }


    // adding the user's name to all pages
    @ModelAttribute
    public void addNameAttribute(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String name = "";
            String email = "";
            if (principal instanceof CustomOAuth2User customOAuth2User) {
                // handle Google login
                name = customOAuth2User.getName();
                email = customOAuth2User.getEmail();
            } else if (principal instanceof UserDetails) {
                // handle local login
                User user = userRepository.findUserByEmail(((UserDetails) principal).getUsername());
                name = user.getUsername();
                email = user.getEmail();
            }
            model.addAttribute("name", name);
            model.addAttribute("email", email);
        }
    }

    // add classroom dropdown menu attributes to all pages
    @ModelAttribute
    public void addClassroomDropdownMenuAttributes(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("classrooms", classroomService.getAllClassrooms(authentication));
            User user = userService.getUser(authentication);
            long selectedClassroomId = user.getSelectedClassroomId();
            model.addAttribute("selectedClassroomId", selectedClassroomId);
            if (selectedClassroomId != 0 && classroomService.getAllClassrooms(authentication).size() > 0) {
                String selectedClassroomName = classroomService.getClassroomById(selectedClassroomId).getClassName();
                model.addAttribute("selectedClassroomName", selectedClassroomName);
            } else {
                if (classroomService.getAllClassrooms(authentication).size() > 0 && selectedClassroomId == 0) {
                    model.addAttribute("selectedClassroomName", "Select Classroom");
                } else {
                    model.addAttribute("selectedClassroomName", "Add Classroom");
                }
            }
        }
    }

}
