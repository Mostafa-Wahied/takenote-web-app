package com.myprojects.takenotewebapp.service;

import com.myprojects.takenotewebapp.dto.UserRegistrationDto;
import com.myprojects.takenotewebapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
