package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.ReadingLevel;
import com.mostafawahied.takenotewebapp.model.Student;
import com.mostafawahied.takenotewebapp.repository.MeetingRepository;
import com.mostafawahied.takenotewebapp.repository.ReadingLevelRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ReadingLevelServiceImpl implements ReadingLevelService {

    private final ReadingLevelRepository readingLevelRepository;
    private final StudentService studentService;
    private final MeetingRepository meetingRepository;

    protected ReadingLevelServiceImpl(ReadingLevelRepository readingLevelRepository, StudentService studentService,
                                      MeetingRepository meetingRepository) {
        this.readingLevelRepository = readingLevelRepository;
        this.studentService = studentService;
        this.meetingRepository = meetingRepository;
    }

    @Override
    public void saveReadingLevel(ReadingLevel readingLevel) {
        readingLevelRepository.save(readingLevel);
    }

    @Override
    public void updateReadingLevel(long studentId, Character newLevel) {
        Student student = studentService.getStudentById(studentId);
        ReadingLevel readingLevel = new ReadingLevel();
        readingLevel.setLevel(newLevel);
        readingLevel.setStudent(student);
        readingLevel.setUpdateDate(new Date(System.currentTimeMillis()));
        student.setCurrentReadingLevel(newLevel);

        Meeting meeting = meetingRepository.findTopByStudentIdOrderByDateDesc(studentId);
        if (meeting != null && meeting.getDate().equals(readingLevel.getUpdateDate())) {
            if (readingLevel.getUpdateDate().after(meeting.getDate())) {
                readingLevelRepository.save(readingLevel);
            }
        } else {
            readingLevelRepository.save(readingLevel);
        }
    }

    @Override
    public Character getLatestReadingLevel(Student student) {
        ReadingLevel readingLevel = readingLevelRepository.findTopByStudentOrderByIdDesc(student);
        if (readingLevel != null) {
            return readingLevel.getLevel();
        }
        return null;
    }

    @Override
    public List<Character> alphabetList() {
        return IntStream.rangeClosed('A', 'Z')
                .mapToObj(c -> (char) c)
                .toList();
    }

    @Override
    public void updateReadingLevelForMeeting(Student student, Character readingLevel, Date meetingDate) {
        student.setCurrentReadingLevel(readingLevel);
        ReadingLevel readingLevel1 = new ReadingLevel();
        readingLevel1.setLevel(readingLevel);
        readingLevel1.setStudent(student);
        readingLevel1.setUpdateDate(meetingDate);
        readingLevelRepository.save(readingLevel1);
    }


}
