package com.myprojects.takenotewebapp.controller;

import com.myprojects.takenotewebapp.dto.UserRegistrationDto;
import com.myprojects.takenotewebapp.model.User;
import com.myprojects.takenotewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class UserRegisterationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
//        userService.save(registrationDto);
//        return "redirect:/register?success";
//    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        try {
            userService.save(registrationDto);
            return "redirect:/register?success";
        } catch (DataIntegrityViolationException e) {
            System.out.println(e);
            return "redirect:/register?error";
        } catch (Exception all) {
            System.out.println(all);
            return "redirect:/register?errors";
        }
    }


////    injecting user object into registration.html
//    @ModelAttribute("user")
//    public UserRegistrationDto userRegistrationDto() {
//        return new UserRegistrationDto();
//    }
}
