package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.model.User;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void saveMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingById(long id) {
        Optional<Meeting> optional = meetingRepository.findById(id);

        Meeting meeting = null;
        if (optional.isPresent()) {
            meeting = optional.get();
        } else {
            throw new RuntimeException(("Meeting not found for id: " + id));
        }
        return meeting;
    }

    @Override
    public void saveReading1on1Meeting(Meeting meeting, String id, Character readingLevel, String strength, String teachingPoint, String nextStep, Model model) {
        int theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        meeting.setStudent(student);
        meeting.setSubject("Reading");
        meeting.setType("1:1 - Reading");
        meeting.setSubjectLevel(readingLevel);
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        this.saveMeeting(meeting);
        model.addAttribute("meetingObject", meeting);
    }

    @Override
    public void saveWriting1on1Meeting(Meeting meeting, String id, String strength, String teachingPoint, String nextStep, Model model) {
        int theId = Integer.parseInt(id);
        Student student = studentService.getStudentById(theId);
        System.out.println("This is the student object " + student);
        meeting.setStudent(student);
        meeting.setSubject("Writing");
        meeting.setType("1:1 - Writing");
        meeting.setStrength(strength);
        meeting.setTeachingPoint(teachingPoint);
        meeting.setNextStep(nextStep);
        this.saveMeeting(meeting);
        model.addAttribute("meetingObject", meeting);
    }

    public void saveMultipleGuidedReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Guided Reading");
            newMeeting.setSubjectLevel(readingLevel);
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveMultipleStrategyReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Strategy Group - Reading");
            newMeeting.setSubjectLevel(readingLevel);
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model) {
        // Create a list to store the selected students
        List<Student> students = new ArrayList<>();
        List<Meeting> meetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            students.add(student);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Writing");
            newMeeting.setType("Strategy Group - Writing");
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            meetings.add(newMeeting);
        }
        // Pass the selected students to the follow-up form view as a model attribute
        model.addAttribute("id", ids);
        model.addAttribute("students", students);
        model.addAttribute("meetings", meetings);
    }

    @Override
    public void saveFollowUpMeetings(String[] ids, List<String> strengthList, List<String> nextStepsList) {
        // Iterate over the students and create a Meeting object for each one
        for (int i = 0; i < ids.length; i++) {
            int theId = Integer.parseInt(ids[i]);
            // Retrieve the existing Meeting object by its ID
            Meeting followUpMeeting = this.getMeetingById(theId);
            if (!strengthList.isEmpty()) {
                followUpMeeting.setStrength(strengthList.get(i));
            }
            if (!nextStepsList.isEmpty()) {
                followUpMeeting.setNextStep(nextStepsList.get(i));
            }
            this.saveMeeting(followUpMeeting);
        }
    }

    @Override
    public Map<String, Map<String, Integer>> getReadingMeetingCountByStudentAndType(Authentication authentication) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        Map<Long, String> studentIdToNameMap = new HashMap<>();
        // Get a list of all students
        List<Student> students = studentService.getAllStudents(authentication);
        // Initialize the result map with all students
        for (Student student : students) {
            String studentName = student.getFirstName() + " " + student.getLastName();
            long studentId = student.getId();
            result.put(studentName, new HashMap<>());
            studentIdToNameMap.put(studentId, studentName);
        }
        List<Meeting> meetings = meetingRepository.findAll();
        for (Meeting meeting : meetings) {
            if (meeting.getSubject().equals("Reading")) {
                Long studentId = meeting.getStudent().getId();
                String meetingType = meeting.getType();
                if (studentIdToNameMap.containsKey(studentId)) {
                    String studentName = studentIdToNameMap.get(studentId);
                    if (result.containsKey(studentName)) {
                        Map<String, Integer> meetingTypes = result.get(studentName);
                        if (meetingTypes.containsKey(meetingType)) {
                            meetingTypes.put(meetingType, meetingTypes.get(meetingType) + 1);
                        } else {
                            meetingTypes.put(meetingType, 1);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Map<String, Integer>> getWritingMeetingCountByStudentAndType(Authentication authentication) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        Map<Long, String> studentIdToNameMap = new HashMap<>();
        // Get a list of all students
        List<Student> students = studentService.getAllStudents(authentication);
        // Initialize the result map with all students
        for (Student student : students) {
            String studentName = student.getFirstName() + " " + student.getLastName();
            long studentId = student.getId();
            result.put(studentName, new HashMap<>());
            studentIdToNameMap.put(studentId, studentName);
        }
        List<Meeting> meetings = meetingRepository.findAll();
        for (Meeting meeting : meetings) {
            if (meeting.getSubject().equals("Writing")) {
                Long studentId = meeting.getStudent().getId();
                String meetingType = meeting.getType();
                if (studentIdToNameMap.containsKey(studentId)) {
                    String studentName = studentIdToNameMap.get(studentId);
                    if (result.containsKey(studentName)) {
                        Map<String, Integer> meetingTypes = result.get(studentName);
                        if (meetingTypes.containsKey(meetingType)) {
                            meetingTypes.put(meetingType, meetingTypes.get(meetingType) + 1);
                        } else {
                            meetingTypes.put(meetingType, 1);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getMeetingCountByType(Authentication authentication) {
        // Get a list of all students associated with the Principal
        List<Student> students = studentService.getAllStudents(authentication);
        // Extract the student IDs from the list of students
        List<Long> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());
        // Get the meeting count by type for the given student IDs
        List<Object[]> meetingCountByType = meetingRepository.getMeetingCountByType(studentIds);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] meetingCount : meetingCountByType) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", meetingCount[0]);
            map.put("count", meetingCount[1]);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getWritingMeetingCountByStudentBySubject(Authentication authentication) {
        List<Object[]> meetingCountByStudent = meetingRepository.getWritingMeetingsByStudentBySubject();
        List<Map<String, Object>> result = new ArrayList<>();
        List<Student> students = studentService.getAllStudents(authentication);
        // Create a map of student names to meeting counts
        Map<String, Long> studentMeetingCounts = new HashMap<>();
        for (Object[] meetingCount : meetingCountByStudent) {
            String studentName = meetingCount[0] + " " + meetingCount[1];
            Long count = (Long) meetingCount[2];
            studentMeetingCounts.put(studentName, count);
        }
        // Add a map to the result for each student
        for (Student student : students) {
            String studentName = student.getFirstName() + " " + student.getLastName();
            Long count = studentMeetingCounts.getOrDefault(studentName, 0L);
            Map<String, Object> map = new HashMap<>();
            map.put(studentName, count);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getReadingMeetingCountByStudentBySubject(Authentication authentication) {
        List<Object[]> readingMeetingsCountByStudent = meetingRepository.getReadingMeetingsByStudentBySubject();
        List<Map<String, Object>> result = new ArrayList<>();
        List<Student> students = studentService.getAllStudents(authentication);
        // Create a map of student names to meeting counts
        Map<String, Long> studentReadingMeetingCounts = new HashMap<>();
        for (Object[] meetingCount : readingMeetingsCountByStudent) {
            String studentName = meetingCount[0] + " " + meetingCount[1];
            Long count = (Long) meetingCount[2];
            studentReadingMeetingCounts.put(studentName, count);
        }
        // Add a map to the result for each student
        for (Student student : students) {
            String studentName = student.getFirstName() + " " + student.getLastName();
            Long count = studentReadingMeetingCounts.getOrDefault(studentName, 0L);
            Map<String, Object> map = new HashMap<>();
            map.put(studentName, count);
            result.add(map);
        }
        return result;
    }

    // get the average subject level progress for the logged in user
    @Override
    public List<Map<String, Object>> getAverageSubjectLevelProgress(Authentication authentication) {
        // Get the email from the principal
        String email = studentService.getUserEmailFromAuthentication(authentication);
        // Find the user by email from the userRepository
        User user = userRepository.findUserByEmail(email);
        // Call the meetingRepository with the user parameter
        List<Object[]> averageSubjectLevelProgress = meetingRepository.getAverageSubjectLevelProgress(user);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] progress : averageSubjectLevelProgress) {
            Date date = (Date) progress[0];
            Double avgSubjectLevel = (Double) progress[1];
            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("avgSubjectLevel", avgSubjectLevel);
            result.add(map);
        }
        return result;
    }

    // get the average subject level progress for 1 student
    @Override
    public List<Map<String, Object>> getStudentAverageSubjectLevelProgress(long studentId) {
        List<Object[]> averageSubjectLevelProgress = meetingRepository.getStudentAverageSubjectLevelProgress(studentId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] progress : averageSubjectLevelProgress) {
            Date date = (Date) progress[0];
            Double avgSubjectLevel = (Double) progress[1];
            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("avgSubjectLevel", avgSubjectLevel);
            result.add(map);
        }
        return result;
    }

    // get the meetings number for the logged in user
    @Override
    public int getMeetingCount(Authentication authentication) {
        // Get the email from the principal
        String email = studentService.getUserEmailFromAuthentication(authentication);
        // Find the user by email from the userRepository
        User user = userRepository.findUserByEmail(email);
        // Call the meetingRepository with the user parameter
        return meetingRepository.getMeetingCount(user);
    }

    // get the average reading level for all meetings for the logged in user
    @Override
    public Float getAverageReadingLevel(Authentication authentication) {
        // Get the email from the principal
        String email = studentService.getUserEmailFromAuthentication(authentication);
        // Find the user by email from the userRepository
        User user = userRepository.findUserByEmail(email);
        // Call the meetingRepository with the user parameter
        Float averageReadingLevel = meetingRepository.getAverageReadingLevel(user);
        if (averageReadingLevel == null) {
            // handle the situation where no data was returned
            return (float) 0; // return a default value
        }
        return averageReadingLevel;
    }

}
