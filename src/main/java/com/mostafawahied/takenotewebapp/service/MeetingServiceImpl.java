package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> findLatestMeetings() {
        return meetingRepository.findLatestMeetings();
    }



}
