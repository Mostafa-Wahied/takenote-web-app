package com.myprojects.takenotewebapp.service;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.Student;

import java.sql.Date;
import java.util.List;

public interface MeetingService {
    List<Meeting> getAllMeetings();

    void saveMeeting(Meeting meeting);

    List<Meeting> findLatestMeetings();


}
