package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;

import java.util.Collection;
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
//    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m where m.subject = 'Reading' and m.student.classroom in :classrooms GROUP BY m.date ORDER BY m.date")
//    List<Object[]> getAverageSubjectLevelProgress(@Param("classrooms") Collection<Classroom> classrooms);
    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m where m.subject = 'Reading' and m.student.classroom.id = :classroomId GROUP BY m.date ORDER BY m.date")
    List<Object[]> getAverageSubjectLevelProgress(@Param("classroomId") long classroomId);


    // get meetings of the students for a given user
    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' and s.id = :studentId GROUP BY m.date ORDER BY m.date")
    List<Object[]> getStudentAverageSubjectLevelProgress(long studentId);

    // get all meetings for the students of a given user
    @Query("select count (m) from Meeting m where m.student.classroom in :classrooms")
    int getMeetingCount(@Param("classrooms") Collection<Classroom> classrooms);

    // get the meetings of a selected classroom id
    @Query("select count (m) from Meeting m where m.student.classroom.id = :classroomId")
    int getMeetingCountByClassroomId(@Param("classroomId") long classroomId);


    // get the average reading level for all meetings for the logged in user
    @Query("select AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) from Meeting m where m.subject = 'Reading' and m.student.classroom in :classrooms")
    Float getAverageReadingLevel(@Param("classrooms") Collection<Classroom> classrooms);

    // get the average reading level for the meeting of the selected classroom id
    @Query("select AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) from Meeting m where m.subject = 'Reading' and m.student.classroom.id = :classroomId")
    Float getAverageReadingLevelByClassroomId(@Param("classroomId") long classroomId);
}
