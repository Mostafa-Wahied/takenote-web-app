package com.mostafawahied.takenotewebapp.config;

import com.mostafawahied.takenotewebapp.model.AuthenticationProvider;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getFullName();

        User user = userService.findUserByEmail(email);

        if (user == null) {
            userService.createNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
        } else {
            userService.updateUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.GOOGLE);

            System.out.println("Logged in User Email: " + email);

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
