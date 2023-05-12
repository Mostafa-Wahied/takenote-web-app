package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.security.Principal;
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

    void saveMultipleStrategyReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model);

    void saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model);

    void saveFollowUpMeetings(String[] ids, List<String> strengthList, List<String> nextStepsList);

    // for reading meeting count by student and type bar chart
    Map<String, Map<String, Integer>> getReadingMeetingCountByStudentAndType(Principal principal);

    // for writing meeting count by student and type bar chart
    Map<String, Map<String, Integer>> getWritingMeetingCountByStudentAndType(Principal principal);

    // for meeting count by type pie chart
    List<Map<String, Object>> getMeetingCountByType(Principal principal);

    // for reading meeting count by student by subject bar chart
    List<Map<String, Object>> getWritingMeetingCountByStudentBySubject(Principal principal);

    // for writing meeting count by student by subject bar chart
    List<Map<String, Object>> getReadingMeetingCountByStudentBySubject(Principal principal);

    // for average subject level progress line chart
    List<Map<String, Object>> getAverageSubjectLevelProgress(Principal principal);

    // get the meetings number for the logged in user
    int getMeetingCount(Principal principal);
}


