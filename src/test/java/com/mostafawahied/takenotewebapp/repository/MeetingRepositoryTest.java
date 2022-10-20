package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MeetingRepositoryTest {

    @Autowired
    public MeetingRepository meetingRepository;

    //    JUnit test for find meeting by id
    @Test
    void findMeetingByIdTest() {
        Long actual = 33L;
        Optional<Meeting> meeting = meetingRepository.findById(actual);
        Long expected = meeting.get().getMeetingNumber();

        Assertions.assertThat(expected.equals(actual));
    }
}
