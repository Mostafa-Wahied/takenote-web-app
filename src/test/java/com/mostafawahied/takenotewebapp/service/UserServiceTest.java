package com.mostafawahied.takenotewebapp.service;

import com.mostafawahied.takenotewebapp.model.Meeting;
import com.mostafawahied.takenotewebapp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

//    JUnit test for finding a user by id
    @Test
    public void findUserByIdTest() {
        Long actual = 1L;
        User user = userService.findUserById(actual);
        Long expected = user.getId();

        Assertions.assertThat(expected.equals(actual));
    }
}
