package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "admin name", "ADMIN");
    }

    @Test
    public void userTest_Update() {
        //ARRANGE
        user.setId(1);
        user.setFullname("new name");
        //ACT
        user = userRepository.save(user);
        //ASSERT
        assertTrue(user.getFullname().equals("new name"));

    }

    @Test
    public void userTest_FindAll() {
        //ACT
        List<User> listResult = userRepository.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(2);
    }

    @Test
    public void userTest_Delete() {
        //ARRANGE
        user.setId(2);
        //ACT
        userRepository.delete(user);
        //ASSERT
        Optional<User> result = userRepository.findById(2);
        assertFalse(result.isPresent());
    }
}
