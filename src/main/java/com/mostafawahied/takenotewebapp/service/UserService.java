package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.model.AuthenticationProvider;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findUserById(long id);


    User findUserByEmail(String email);
    void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider);

    void updateUserAfterOAuthLoginSuccess(User userEntity, String name, AuthenticationProvider authenticationProvider);
}
