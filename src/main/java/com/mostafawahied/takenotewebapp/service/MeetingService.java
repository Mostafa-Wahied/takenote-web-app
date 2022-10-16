package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;

import java.util.List;

public interface MeetingService {
    List<Meeting> getAllMeetings();

    void saveMeeting(Meeting meeting);

    List<Meeting> findLatestMeetings();


}
