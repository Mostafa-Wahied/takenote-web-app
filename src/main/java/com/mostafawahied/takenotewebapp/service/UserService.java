package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
