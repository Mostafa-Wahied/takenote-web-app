package com.mostafawahied.takenotewebapp.controller;

import com.mostafawahied.takenotewebapp.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    //    viewHomePage
    @GetMapping("/")
    public String homePage(Model model) throws Exception {
        //        for navigation active state
        model.addAttribute("activePage", "home");
        return "index";
    }
    //    view about page
    @GetMapping("/about")
    public String viewAboutPage(Model model) throws Exception {
//        for navigation active state
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //    view privacy-policy page
    @GetMapping("/privacy-policy")
    public String viewAPrivacyPolicyPage(Model model) throws Exception {
//        for navigation active state
        model.addAttribute("activePage", "privacy-policy");
        return "privacy-policy";
    }

    @GetMapping("/whats-new/content")
    public ResponseEntity<String> getWhatsNewContent() {
        try {
            String content = Utility.getWhatsNewContent();
            logger.debug("Content loaded successfully");
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            logger.error("Error loading content", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading content");
        }
    }
}
