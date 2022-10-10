package com.myprojects.takenotewebapp.repository;

import com.myprojects.takenotewebapp.model.Meeting;
import com.myprojects.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(nativeQuery = true,
            value = "select MAX(m.date) as \"max date\", subject, type, next_step, strength, subject_level, teaching_point, student_id from meetings m group by student_id;")
    Object[] findLatestMeetings();
    // --------------- end of experimental --------------


}
