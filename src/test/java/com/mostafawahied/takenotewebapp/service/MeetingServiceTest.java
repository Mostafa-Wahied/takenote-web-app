package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

//    JUnit test for finding a meeting by id
    @Test
    public void findMeetingByIdTest() {
        Long actual = 33L;
        Meeting meeting = meetingService.getMeetingById(actual);
        Long expected = meeting.getMeetingNumber();

        Assertions.assertThat(expected.equals(actual));
    }
}
