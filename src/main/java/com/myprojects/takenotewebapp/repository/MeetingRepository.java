package com.myprojects.takenotewebapp.repository;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.MeetingId;
import com.myprojects.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, MeetingId> {
    List<Meeting> findMeetingsBySubject(String subject);
}
