package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.exception.UserNotFoundException;
import com.mostafawahied.takenotewebapp.model.AuthenticationProvider;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);

    void updateUser(User user);

    User findUserById(long id);

    User findUserByEmail(String email);

    void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider);

    void updateUserAfterOAuthLoginSuccess(User userEntity, String name, AuthenticationProvider authenticationProvider);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    User getUser(Authentication authentication);

    long getUserSelectedClassroomId(Authentication authentication);
}
