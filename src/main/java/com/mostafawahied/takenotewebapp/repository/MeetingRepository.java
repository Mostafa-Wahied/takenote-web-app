package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
//    final String latestMeetings = "select new SimpleDateFormat(\"dd/MM/yyyy\").parse(max(date)), subject, meeting_number, type, next_step, strength, subject_level, teaching_point, student_id from meetings m group by student_id;";

//    Date maxdate = new Date(Long.MAX_VALUE);
    @Query(nativeQuery = true, value = "select max(date), subject, meeting_number, type, next_step, strength, subject_level, teaching_point, student_id from meetings group by student_id")
    List<Meeting> findLatestMeetings();


    // --------------- end of experimental --------------


}
