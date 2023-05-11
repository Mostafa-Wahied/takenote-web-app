package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;

import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    //    Using custom query to get meetings
    @Query("SELECT m FROM Meeting m JOIN FETCH m.student")
    List<Meeting> findAll();

    //
    @Query("SELECT m.type, COUNT(m) FROM Meeting m GROUP BY m.type")
    List<Object[]> getMeetingCountByType();

    @Query("select s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Writing' group by m.student.id")
    List<Object[]> getWritingMeetingsByStudent();

    @Query("select s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' group by m.student.id")
    List<Object[]> getReadingMeetingsByStudent();
}
