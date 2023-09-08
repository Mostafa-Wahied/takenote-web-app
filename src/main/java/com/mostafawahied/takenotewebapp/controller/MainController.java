package com.mostafawahied.takenotewebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
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
}
