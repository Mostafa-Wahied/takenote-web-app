package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.*;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final StudentService studentService;
    private final MeetingRepository meetingRepository;
    private final UserService userService;
    private final ReadingLevelService readingLevelService;

    public MeetingServiceImpl(StudentService studentService, MeetingRepository meetingRepository, UserService userService, ReadingLevelService readingLevelService) {
        this.studentService = studentService;
        this.meetingRepository = meetingRepository;
        this.userService = userService;
        this.readingLevelService = readingLevelService;
    }

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

        Meeting meeting;
        if (optional.isPresent()) {
            meeting = optional.get();
        } else {
            throw new RuntimeException(("Meeting not found for id: " + id));
        }
        return meeting;
    }

    @Override
    public Meeting saveReading1on1Meeting(Meeting meeting, String id, Character readingLevel, String strength, String teachingPoint, String nextStep) {
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

        readingLevelService.updateReadingLevelForMeeting(student, readingLevel, meeting.getDate());

        return meeting;
    }

    @Override
    public Meeting saveWriting1on1Meeting(Meeting meeting, String id, String strength, String teachingPoint, String nextStep) {
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
        return meeting;
    }

    public List<Meeting> saveMultipleGuidedReadingMeetings(String[] ids, Date date, Character readingLevel, String teachingPoint, Model model) {
        List<Meeting> createdMeetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Guided Reading");
            newMeeting.setSubjectLevel(readingLevel);
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);

            readingLevelService.updateReadingLevelForMeeting(student, readingLevel, date);
            createdMeetings.add(newMeeting);
        }

        return createdMeetings;
    }

    @Override
    public List<Meeting> saveMultipleStrategyReadingMeetings(String[] ids, Date date, String teachingPoint, Model model) {
        List<Meeting> createdMeetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Reading");
            newMeeting.setType("Strategy Group - Reading");
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            createdMeetings.add(newMeeting);
        }

        return createdMeetings;
    }

    @Override
    public List<Meeting> saveMultipleStrategyWritingMeetings(String[] ids, Date date, String teachingPoint, Model model) {
        List<Meeting> createdMeetings = new ArrayList<>();
        // Iterate through the selected student IDs and get the corresponding students
        for (String id : ids) {
            Meeting newMeeting = new Meeting();
            int theId = Integer.parseInt(id);
            Student student = studentService.getStudentById(theId);
            newMeeting.setStudent(student);
            newMeeting.setDate(date);
            newMeeting.setSubject("Writing");
            newMeeting.setType("Strategy Group - Writing");
            newMeeting.setTeachingPoint(teachingPoint);
            this.saveMeeting(newMeeting);
            createdMeetings.add(newMeeting);
        }

        return createdMeetings;
    }

    // Helper method to

    @Override
    public void saveFollowUpMeetings(String[] ids, List<String> strengthList, List<String> nextStepsList, Model model) {
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
        model.addAttribute("meetingObject", this.getMeetingById(Long.parseLong(ids[0])).getType());

    }

    // Get the reading meetings count by student and type for the bar chart
    @Override
    public Map<String, Map<String, Integer>> getReadingMeetingCountByStudentAndType(Authentication authentication) {
        return getMeetingCountByType(authentication, "Reading");
    }

    // Get the writing meetings count by student and type for the bar chart
    @Override
    public Map<String, Map<String, Integer>> getWritingMeetingCountByStudentAndType(Authentication authentication) {
        return getMeetingCountByType(authentication, "Writing");
    }

    // Helper method to get the meetings count by student and type
    private Map<String, Map<String, Integer>> getMeetingCountByType(Authentication authentication, String subject) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        long classroomId = userService.getUserSelectedClassroomId(authentication);
        List<Student> students = studentService.getAllStudentsByClassroomId(classroomId);
        // Initialize the result map with all students
        students.forEach(student -> {
            String studentName = student.getFirstName() + " " + student.getLastName();
            result.put(studentName, new HashMap<>());
        });
        List<Meeting> meetings = meetingRepository.findMeetingsBySubjectAndClassroomId(subject, classroomId);
        for (Meeting meeting : meetings) {
            String studentName = meeting.getStudent().getFirstName() + " " + meeting.getStudent().getLastName();
            String meetingType = meeting.getType();
            result.computeIfAbsent(studentName, k -> new HashMap<>())
                    .merge(meetingType, 1, Integer::sum);
        }
        return result;
    }

    // Pie Chart
    @Override
    public List<Map<String, Object>> getMeetingCountByType(Authentication authentication) {
        // Get a list of all students associated with the Principal
        long classroomId = userService.getUserSelectedClassroomId(authentication);
        List<Student> students = studentService.getAllStudentsByClassroomId(classroomId);
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

    // Get the reading meetings count by student and subject for the bar chart
    @Override
    public List<Map<String, Object>> getReadingMeetingCountByStudentBySubject(Authentication authentication) {
        List<Object[]> readingMeetingsCountByStudent = meetingRepository.getReadingMeetingsCountByStudentBySubject();
        return getMeetingCountByStudentAndSubject(readingMeetingsCountByStudent, authentication);
    }

    // Get the writing meetings count by student and subject for the bar chart
    @Override
    public List<Map<String, Object>> getWritingMeetingCountByStudentBySubject(Authentication authentication) {
        List<Object[]> writingMeetingCountByStudent = meetingRepository.getWritingMeetingsByStudentBySubject();
        return getMeetingCountByStudentAndSubject(writingMeetingCountByStudent, authentication);
    }

    // Helper method to get the meetings count by student and subject
    private List<Map<String, Object>> getMeetingCountByStudentAndSubject(List<Object[]>
                                                                                 meetingCountByStudent, Authentication authentication) {
        long classroomId = userService.getUserSelectedClassroomId(authentication);
        List<Student> students = studentService.getAllStudentsByClassroomId(classroomId);
        // Create a map of student names to meeting counts
        Map<String, Long> studentMeetingCounts = new HashMap<>();
        for (Object[] meetingCount : meetingCountByStudent) {
            String studentName = meetingCount[0] + " " + meetingCount[1];
            Long count = (Long) meetingCount[2];
            studentMeetingCounts.put(studentName, count);
        }

        List<Map<String, Object>> result = new ArrayList<>();
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

    // get the average subject level progress for all students in the logged-in user's classroom
    @Override
    public List<Map<String, Object>> getAverageSubjectLevelProgress(Authentication authentication) {
        long classroomId = userService.getUserSelectedClassroomId(authentication);
        List<Object[]> averageSubjectLevelProgressFromMeetings = meetingRepository.getAverageSubjectLevelProgressFromMeetings(classroomId);
        List<Object[]> averageSubjectLevelProgressFromReadingLevels = meetingRepository.getAverageSubjectLevelProgressFromReadingLevels(classroomId);

        return getAverageSubjectLevelProgress(averageSubjectLevelProgressFromMeetings, averageSubjectLevelProgressFromReadingLevels);
    }

    // get the average subject level progress for 1 student
    @Override
    public List<Map<String, Object>> getStudentAverageSubjectLevelProgress(long studentId) {
        List<Object[]> averageSubjectLevelProgressFromMeetings = meetingRepository.getStudentAverageSubjectLevelProgressFromMeetings(studentId);
        List<Object[]> averageSubjectLevelProgressFromReadingLevels = meetingRepository.getStudentAverageSubjectLevelProgressFromReadingLevels(studentId);

        return getAverageSubjectLevelProgress(averageSubjectLevelProgressFromMeetings, averageSubjectLevelProgressFromReadingLevels);
    }

    // Helper method to get the average subject level progress
    private List<Map<String, Object>> getAverageSubjectLevelProgress(List<Object[]>
                                                                             averageSubjectLevelProgressFromMeetings, List<Object[]> averageSubjectLevelProgressFromReadingLevels) {
        Map<Date, List<Double>> averageByDate = new HashMap<>();
        // Merge the two lists and group by date
        Stream.concat(averageSubjectLevelProgressFromMeetings.stream(), averageSubjectLevelProgressFromReadingLevels.stream())
                .filter(Objects::nonNull)
                .filter(arr -> arr[0] != null && arr[1] != null)
                .forEach(arr -> averageByDate.computeIfAbsent((Date) arr[0], k -> new ArrayList<>()).add((Double) arr[1]));

        List<Map<String, Object>> result;
        // Convert to list of maps sorted by date
        result = averageByDate.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.getKey());
                    map.put("avgSubjectLevel", entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
                    return map;
                })
                .sorted(Comparator.comparing(map -> (Date) map.get("date")))
                .collect(Collectors.toList());
        return result;
    }

    // get all the meetings number for the logged-in user
    @Override
    public int getAllMeetingsCountByUser(Authentication authentication) {
        User user = userService.getUser(authentication);
        List<Classroom> classrooms = user.getClassrooms();
        return meetingRepository.getMeetingCount(classrooms);
    }

    // get the meetings for the selected classroom
    @Override
    public int getMeetingsByClassroom(long classroomId) {
        return meetingRepository.getMeetingCountByClassroomId(classroomId);
    }

    // get the average reading level for all meetings for the logged-in user
    @Override
    public Float getAverageReadingLevelBySelectedClassroomId(long classroomId) {
        Float averageReadingLevel = meetingRepository.getAverageReadingLevelByClassroomId(classroomId);
        if (averageReadingLevel == null) {
            // handle the situation where no data was returned
            return (float) 0; // return a default value
        }
        return averageReadingLevel;
    }

    @Override
    public List<Meeting> getMeetingsForStudents(List<Student> students) {
        return meetingRepository.findMeetingsByStudentIn(students);
    }

}
