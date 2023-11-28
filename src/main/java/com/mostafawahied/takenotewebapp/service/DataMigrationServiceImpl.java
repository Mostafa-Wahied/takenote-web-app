package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Config;
import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.ReadingLevel;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.repository.ConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {
    private final ConfigRepository configRepository;
    private final StudentService studentService;
    private final ReadingLevelService readingLevelService;
    private final MeetingService meetingService;

    public DataMigrationServiceImpl(ConfigRepository configRepository, StudentService studentService, ReadingLevelService readingLevelService, MeetingService meetingService) {
        this.configRepository = configRepository;
        this.studentService = studentService;
        this.readingLevelService = readingLevelService;
        this.meetingService = meetingService;
    }


    @PostConstruct
    @Transactional
    public void init() {
        Config config = configRepository.findById("migrateReadingLevels").orElse(new Config("migrateReadingLevels", "false"));
        if (config.getValue().equals("false")) {
            migrateReadingLevels();
            config.setValue("true");
            configRepository.save(config);
        }
    }

    @Override
    @Transactional
    public void migrateReadingLevels() {
        List<Student> students = studentService.getAllStudentsForMigration();

        for (Student student : students) {
            List<Meeting> meetings = meetingService.getMeetingsForStudent(student.getId());
            meetings.removeIf(meeting -> !meeting.getSubject().equals("Reading"));

            if(!meetings.isEmpty()) {
                Meeting latestReadingMeeting = meetings.stream()
                        .max(Comparator.comparing(Meeting::getDate))
                        .orElseThrow();

                ReadingLevel readingLevel = new ReadingLevel();
                readingLevel.setLevel(latestReadingMeeting.getSubjectLevel());
                readingLevel.setUpdateDate(latestReadingMeeting.getDate());
                readingLevel.setStudent(student);

                readingLevelService.saveReadingLevel(readingLevel);

                student.setCurrentReadingLevel(latestReadingMeeting.getSubjectLevel());
                studentService.updateStudent(student);
            }
        }
    }
}
