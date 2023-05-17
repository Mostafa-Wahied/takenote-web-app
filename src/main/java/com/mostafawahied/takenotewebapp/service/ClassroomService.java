package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Classroom;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClassroomService {
    // a helper method to obtain the email address of the logged in user depending on the user is logged in using google or not
    String getUserEmailFromAuthentication(Authentication authentication);


    List<Classroom> getAllClassrooms(Authentication authentication);

    void saveClassroom(Classroom classroom, Authentication authentication);

    Classroom getClassroomById(long id);
}
