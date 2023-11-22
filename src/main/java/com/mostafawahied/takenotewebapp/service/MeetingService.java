package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface MeetingService {
    List<Meeting> getAllMeetings();

    void saveMeeting(Meeting meeting);

    Meeting getMeetingById(long id);

    void saveReading1on1Meeting(Meeting meeting, String id, Character readingLevel, String strength, String teachingPoint, String nextStep, Model model);

    void saveWriting1on1Meeting(Meeting meeting, String id, String strength, String teachingPoint, String nextStep, Model model);

    void saveMultipleGuidedReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model);

    void saveMultipleStrategyReadingMeetings(String[] ids, Date date,  String teachingPoint, Model model);

    void saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model);

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
}


