package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.config.CustomOAuth2User;
import com.mostafawahied.takenotewebapp.exception.ResourceNotFoundException;
import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.repository.ClassroomRepository;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    // a helper method to obtain the email address of the logged in user depending on the user is logged in using google or not
    @Override
    public String getUserEmailFromAuthentication(Authentication authentication) {
        // Obtain the principal object associated with the authenticated user
        Object principal = authentication.getPrincipal();
        String email = null;
        if (principal instanceof CustomOAuth2User customOAuth2User) {
            // Cast the principal object to CustomOAuth2User and obtain the email address
            email = customOAuth2User.getEmail();
        } else if (principal instanceof UserDetails) {
            // Cast the principal object to UserDetails and obtain the email address
            UserDetails userDetails = (UserDetails) principal;
            email = userDetails.getUsername();
        }
        return email;
    }

    @Override
    public List<Classroom> getAllClassrooms(Authentication authentication) {
        // Obtain the email address of the user from the CustomOAuth2User object
        String email = getUserEmailFromAuthentication(authentication);
        // Find the user by email
        return userRepository.findUserByEmail(email).getClassrooms();
    }

    @Override
    public void saveClassroom(Classroom classroom, Authentication authentication) {
        // Obtain the email address of the user from the CustomOAuth2User object
        String email = getUserEmailFromAuthentication(authentication);
        // Find the user by email
        classroom.setUser(userRepository.findUserByEmail(email));
        // Save the classroom
        classroomRepository.save(classroom);
    }

    @Override
    public Classroom getClassroomById(long id) {
        Optional<Classroom> optional = classroomRepository.findById(id);
        Classroom classroom;
        if (optional.isPresent()) {
            classroom = optional.get();
        } else {
            throw new ResourceNotFoundException("Classroom not found for id: " + id);
        }
        return classroom;
    }

}
