package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    //    JUnit test for find meeting by id
    @Test
    void findMeetingByIdTest() {
        Long actual = 1L;
        Optional<User> user = userRepository.findById(actual);
        Long expected = user.get().getId();

        Assertions.assertThat(expected.equals(actual));
    }

//    JUnit test for find user by username
//    @Test
//    void findUserByUsernameTest(){
//        actua
////        User actual = new User("usernameTest", "passwordTest");
//        System.out.println(actual.getUsername());
//        User expected = userRepository.findByUsername(actual.getUsername());
//        System.out.println(expected);
//        Assertions.assertThat(expected.equals(actual));
//    }

    //findByUsername() test
    @Test
    void findUserByUsernameTest() {
        String actual="user";
        User user=userRepository.findByUsername(actual);
        String expected=user.getUsername();
        Assertions.assertThat(expected.equals(actual));
        //Assert.assertEquals(expected,actual);
    }
//
//    //parameterized test for findByUsername() method
    @ParameterizedTest
    @ValueSource(strings={"user"})
    void findUserByUsernameParamTest(String actual) {
        String expected=userRepository.findByUsername(actual).getUsername();
        Assertions.assertThat(expected.equals(actual));
    }

}
