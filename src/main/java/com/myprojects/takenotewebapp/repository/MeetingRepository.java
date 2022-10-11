package com.myprojects.takenotewebapp.repository;

import com.myprojects.takenotewebapp.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    final String latestMeetings = "select MAX(m.date) as \"max date\", subject, meeting_number, type, next_step, strength, subject_level, teaching_point, student_id from meetings m group by student_id;";

    @Query(nativeQuery = true, value = latestMeetings)
    List<Meeting> findLatestMeetings();


    // --------------- end of experimental --------------


}
