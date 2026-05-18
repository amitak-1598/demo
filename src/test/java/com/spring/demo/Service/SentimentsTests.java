package com.spring.demo.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.spring.demo.Repository.UserRepositoryImpl;
import com.spring.demo.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SentimentsTests {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserScheduler userScheduler;
    
    

    @Test
    void test() {
        List<User> users = userRepositoryImpl.getUsersHavingEmailAndSA();
        log.info("Users are " + users);
        assertNotNull(users);

    }

    @Test
    void fetchSentiments() {
        userScheduler.fetchSentimentsAndSendMail();


    }



}
