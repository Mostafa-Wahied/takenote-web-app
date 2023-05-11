package com.mostafawahied.takenotewebapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {
    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;
            // Get the user's profile picture URL from the OAuth2 authentication
            String pictureUrl = oauth2Authentication.getPrincipal().getAttributes().get("picture").toString();
            model.addAttribute("pictureUrl", pictureUrl);
        }
    }
}
