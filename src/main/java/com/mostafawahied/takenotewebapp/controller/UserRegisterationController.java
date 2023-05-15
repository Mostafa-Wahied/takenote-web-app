package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.exception.DuplicateEmailException;
import com.mostafawahied.takenotewebapp.exception.DuplicateUsernameException;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/register")
public class UserRegisterationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        try {
            userService.save(registrationDto);
            // Authenticate the user
            authenticateUser(registrationDto.getEmail(), registrationDto.getPassword());
            return "redirect:/dashboard";
        } catch (DuplicateEmailException e) {
            // Redirect with emailError parameter if there is a duplicate email
            return "redirect:/register?emailError";
        } catch (DuplicateUsernameException e) {
            // Redirect with usernameError parameter if there is a duplicate username
            return "redirect:/register?usernameError";
        } catch (Exception allExceptions) {
            System.out.println(allExceptions.getMessage());
            return "redirect:/register?errors";
        }
    }

    // helper method for the auto login
    private void authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
