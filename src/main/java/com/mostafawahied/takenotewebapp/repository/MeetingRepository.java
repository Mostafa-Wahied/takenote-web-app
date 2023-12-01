package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAll();

    // get meetings of the students with the given ids
    @Query("SELECT m.type, COUNT(m) FROM Meeting m WHERE m.student.id IN :studentIds GROUP BY m.type")
    List<Object[]> getMeetingCountByType(@Param("studentIds") List<Long> studentIds);

    @Query("select s.id, s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Writing' group by m.student.id")
    List<Object[]> getWritingMeetingsByStudentBySubject();

    @Query("select s.id, s.firstName, s.lastName, count(*) as meeting_count from Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' group by m.student.id")
    List<Object[]> getReadingMeetingsCountByStudentBySubject();

    // get meetings of the students for a given user
//    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m where m.subject = 'Reading' and m.student.classroom in :classrooms GROUP BY m.date ORDER BY m.date")
//    List<Object[]> getAverageSubjectLevelProgress(@Param("classrooms") Collection<Classroom> classrooms);

    // get the list of reading levels for a classroom from meetings
    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m where m.subject = 'Reading' and m.student.classroom.id = :classroomId GROUP BY m.date ORDER BY m.date")
    List<Object[]> getAverageSubjectLevelProgressFromMeetings(@Param("classroomId") long classroomId);


    // get the list of reading levels for a student from meetings
    @Query("SELECT m.date, AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) FROM Meeting m join Student s on m.student.id = s.id where m.subject = 'Reading' and s.id = :studentId GROUP BY m.date ORDER BY m.date")
    List<Object[]> getStudentAverageSubjectLevelProgressFromMeetings(long studentId);

    // get the list of reading levels for a classroom from reading levels
    @Query("SELECT rl.updateDate, AVG(CAST(ASCII(rl.level) - ASCII('A') + 1 AS float)) FROM ReadingLevel rl where rl.student.classroom.id = :classroomId GROUP BY rl.updateDate ORDER BY rl.updateDate")
    List<Object[]> getAverageSubjectLevelProgressFromReadingLevels(@Param("classroomId") long classroomId);

    // get the list of reading levels for a student from reading levels
    @Query("SELECT rl.updateDate, AVG(CAST(ASCII(rl.level) - ASCII('A') + 1 AS float)) FROM ReadingLevel rl where rl.student.id = :studentId group by rl.updateDate ORDER BY rl.updateDate")
    List<Object[]> getStudentAverageSubjectLevelProgressFromReadingLevels(long studentId);

    // get all meetings for the students of a given user
    @Query("select count (m) from Meeting m where m.student.classroom in :classrooms")
    int getMeetingCount(@Param("classrooms") Collection<Classroom> classrooms);

    // get the meetings of a selected classroom id
    @Query("select count (m) from Meeting m where m.student.classroom.id = :classroomId")
    int getMeetingCountByClassroomId(@Param("classroomId") long classroomId);


//    // get the average reading level for all meetings for the logged in user
//    @Query("select AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) from Meeting m where m.subject = 'Reading' and m.student.classroom in :classrooms")
//    Float getAverageReadingLevel(@Param("classrooms") Collection<Classroom> classrooms);

    // get the average reading level for the meeting of the selected classroom id
    @Query("select AVG(CAST(ASCII(m.subjectLevel) - ASCII('A') + 1 AS float)) from Meeting m where m.subject = 'Reading' and m.student.classroom.id = :classroomId")
    Float getAverageReadingLevelByClassroomId(@Param("classroomId") long classroomId);

    Meeting findTopByStudentIdOrderByDateDesc(long studentId);

    // get the meetings of a selected classroom id and subject for the bar chart
    @Query("select m from Meeting m where m.subject = :subject and m.student.classroom.id = :classroomId")
    List<Meeting> findMeetingsBySubjectAndClassroomId(String subject, long classroomId);

//    // get the average reading level for the meeting of the selected classroom id
//    @Query("select AVG(CAST(ASCII(rl.level) - ASCII('A') + 1 AS double)) from ReadingLevel rl where rl.student.classroom.id = :classroomId")
//    Float getAverageReadingLevelByClassroomId(@Param("classroomId") long classroomId);

    // get the meetings for a list of students using jpa repository
    List<Meeting> findMeetingsByStudentIn(List<Student> students);

    List<Meeting> findMeetingsByStudentId(long studentId);

    // get a list of meeting types
    @Query("select distinct m.type from Meeting m")
    List<String> getMeetingTypes();

    // get a list of meeting types for a given subject
    @Query("select distinct m.type from Meeting m where m.subject = :subject")
    List<String> getMeetingTypesBySubject(String subject);
}
