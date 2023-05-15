package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;

import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    //    Using custom query to get meetings
    @Query("SELECT m FROM Meeting m JOIN FETCH m.student")
    List<Meeting> findAll();

    // get meetings of the students with the given ids
    @Query("SELECT m.type, COUNT(m) FROM Meeting m WHERE m.student.id IN :studentIds GROUP BY m.type")
    List<Object[]> getMeetingCountByType(@Param("studentIds") List<Long> studentIds);

    @Query("select s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Writing' group by m.student.id")
    List<Object[]> getWritingMeetingsByStudentBySubject();

    @Query("select s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' group by m.student.id")
    List<Object[]> getReadingMeetingsByStudentBySubject();

    // get meetings of the students for a given user
    @Query("SELECT m.date, AVG(CAST(ASCII('Z') - ASCII(m.subjectLevel) + 1 AS float)) FROM Meeting m where m.subject = 'Reading' and m.student.user = :user GROUP BY m.date ORDER BY m.date")
    List<Object[]> getAverageSubjectLevelProgress(@Param("user") User user);

    // get meetings of the students for a given user
    @Query("SELECT m.date, AVG(CAST(ASCII('Z') - ASCII(m.subjectLevel) + 1 AS float)) FROM Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' and s.id = :studentId GROUP BY m.date ORDER BY m.date")
    List<Object[]> getStudentAverageSubjectLevelProgress(long studentId);

    // get all meetings for the students of a given user
    @Query("select count (m) from Meeting m where m.student.user = :user")
    int getMeetingCount(@Param("user") User user);

    // get the average reading level for all meetings for the logged in user
    @Query("select avg(CAST(ASCII('Z') - ASCII(m.subjectLevel) + 1 AS float)) from Meeting m where m.subject = 'Reading' and m.student.user = :user")
    Float getAverageReadingLevel(@Param("user") User user);

}
