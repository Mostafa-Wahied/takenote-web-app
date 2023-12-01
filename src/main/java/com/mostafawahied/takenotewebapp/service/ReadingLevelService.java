package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.ReadingLevel;
import com.mostafawahied.takenotewebapp.model.Student;

import java.sql.Date;
import java.util.List;

public interface ReadingLevelService {
    void saveReadingLevel(ReadingLevel readingLevel);
    void updateReadingLevel(long studentId, Character newLevel);
    Character getLatestReadingLevel(Student student);
    ReadingLevel getReadingLevelByStudentIdAndUpdateDate(long id, Date date);
    List<Character> alphabetList();
    void updateReadingLevelForMeeting(Student student, Character readingLevel, Date date);
    void deleteReadingLevelByStudentIdAndDate(long id, Date date);
}
