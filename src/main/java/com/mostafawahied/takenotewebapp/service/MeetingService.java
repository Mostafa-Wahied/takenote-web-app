package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface MeetingService {
    List<Meeting> getAllMeetings();

    void saveMeeting(Meeting meeting);

    Meeting getMeetingById(long id);

    Meeting saveReading1on1Meeting(Meeting meeting, String id, Character readingLevel, String strength, String teachingPoint, String nextStep);

    Meeting saveWriting1on1Meeting(Meeting meeting, String id, String strength, String teachingPoint, String nextStep);

    List<Meeting> saveMultipleGuidedReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model);

    List<Meeting> saveMultipleStrategyReadingMeetings(String[] ids, Date date,  String teachingPoint, Model model);

    List<Meeting> saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model);

    void saveFollowUpMeetings(String[] ids, List<String> strengthList, List<String> nextStepsList, Model model);

    Map<String, Map<String, Integer>> getReadingMeetingCountByStudentAndType(Authentication authentication);

    Map<String, Map<String, Integer>> getWritingMeetingCountByStudentAndType(Authentication authentication);

    List<Map<String, Object>> getMeetingCountByType(Authentication authentication);

    List<Map<String, Object>> getWritingMeetingCountByStudentBySubject(Authentication authentication);

    List<Map<String, Object>> getReadingMeetingCountByStudentBySubject(Authentication authentication);

    // for average subject level progress line chart
    List<Map<String, Object>> getAverageSubjectLevelProgress(Authentication authentication);

    // get the average subject level progress for 1 student
    List<Map<String, Object>> getStudentAverageSubjectLevelProgress(long studentId);

    // get the meetings number for the logged in user
    int getAllMeetingsCountByUser(Authentication authentication);

    // get the meetings for the selected classroom
    int getMeetingsByClassroom(long classroomId);

    // get the average reading level for all meetings for the logged in user
    Float getAverageReadingLevelBySelectedClassroomId(long classroomId);

    List<Meeting> getMeetingsForStudents(List<Student> students);
    List<Meeting> getMeetingsForStudent(long studentId);

    List<String> getMeetingTypes();

    List<String> getMeetingTypesBySubject(String subject);

    void deleteMeetingById(long meetingNumber);

    void updateMeeting(Meeting meeting);
}


