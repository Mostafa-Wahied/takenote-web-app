package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.config.CustomOAuth2User;
import com.mostafawahied.takenotewebapp.dto.UserRegistrationDto;
import com.mostafawahied.takenotewebapp.exception.DuplicateEmailException;
import com.mostafawahied.takenotewebapp.exception.DuplicateUsernameException;
import com.mostafawahied.takenotewebapp.exception.UserNotFoundException;
import com.mostafawahied.takenotewebapp.model.AuthenticationProvider;
import com.mostafawahied.takenotewebapp.model.Role;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User save(UserRegistrationDto registrationDto) {
        // Check if there is already a user with the same email
        User existingUser = userRepository.findUserByEmail(registrationDto.getEmail());
        if (existingUser != null) {
            // Throw a custom exception if there is a duplicate email
            throw new DuplicateEmailException();
        }
        // Check if there is already a user with the same username
        existingUser = userRepository.findByUsername(registrationDto.getUsername());
        if (existingUser != null) {
            // Throw a custom exception if there is a duplicate username
            throw new DuplicateUsernameException();
        }
        User user = new User(
                registrationDto.getUsername(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                List.of(new Role("ROLE_USER"))
        );
        user.setAuthProvider(AuthenticationProvider.LOCAL);
        return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserById(long id) {
        Optional<User> optional = userRepository.findById(id);

        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(("User not found for id: " + id));
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setAuthProvider(provider);
        user.setRoles(List.of(new Role("ROLE_USER")));
        this.userRepository.save(user);
    }

    @Override
    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider authenticationProvider) {
        user.setUsername(name);
        user.setAuthProvider(authenticationProvider);
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // for forgot password
    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findUserByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
    // end of forgot password

    // a helper method to obtain the email address of the logged in user depending on the user is logged in using google or not
    private String getUserEmailFromAuthentication(Authentication authentication) {
        // Obtain the principal object associated with the authenticated user
        Object principal = authentication.getPrincipal();
        String email = null;
        if (principal instanceof CustomOAuth2User customOAuth2User) {
            // Cast the principal object to CustomOAuth2User and obtain the email address
            email = customOAuth2User.getEmail();
        } else if (principal instanceof UserDetails userDetails) {
            // Cast the principal object to UserDetails and obtain the email address
            email = userDetails.getUsername();
        }
        return email;
    }

    // Get user
    @Override
    public User getUser(Authentication authentication) {
        String email = getUserEmailFromAuthentication(authentication);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public long getUserSelectedClassroomId(Authentication authentication) {
        return getUser(authentication).getSelectedClassroomId();
    }


}
