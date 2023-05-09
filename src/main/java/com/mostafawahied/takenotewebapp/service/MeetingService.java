package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    List<Map<String, Object>> getMeetingCountByType();

    //    for meeting count by student
    List<Map<String, Object>> getWritingMeetingCountByStudent(Principal principal);

    List<Map<String, Object>> getReadingMeetingCountByStudent(Principal principal);
}


