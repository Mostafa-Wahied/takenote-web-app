package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.exception.DuplicateEmailException;
import com.mostafawahied.takenotewebapp.exception.DuplicateUsernameException;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
        } catch (DuplicateEmailException e) {
            // Redirect with emailError parameter if there is a duplicate email
            return "redirect:/register?emailError";
        } catch (DuplicateUsernameException e) {
            // Redirect with usernameError parameter if there is a duplicate username
            return "redirect:/register?usernameError";
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
