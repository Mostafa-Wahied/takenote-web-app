package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingById(long id) {
        Optional<Meeting> optional = meetingRepository.findById(id);

        Meeting meeting = null;
        if (optional.isPresent()) {
            meeting = optional.get();
        } else {
            throw new RuntimeException(("Meeting not found for id: " + id));
        }
        return meeting;
    }

}
