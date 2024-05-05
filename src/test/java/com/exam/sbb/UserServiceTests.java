package com.exam.sbb;

import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.QuestionRepository;
import com.exam.sbb.user.UserRepository;
import com.exam.sbb.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        clearData();
        createSampleData();
    }

    private void createSampleData() {
        userService.create("admin", "admin@test.com", "1234");
        userService.create("user1", "user1@test.com", "1234");
        QuestionRepositoryTests.createSampleData(questionRepository);
        AnswerRepositoryTests.createSampleData(questionRepository, answerRepository);
    }

    public static void createSampleData2(UserService userService) {
        userService.create("admin", "admin@test.com", "1234");
        userService.create("user1", "user1@test.com", "1234");
    }

    private void clearData() {
        clearData(userRepository, questionRepository, answerRepository);
    }

    public static void clearData(UserRepository userRepository,
                          QuestionRepository questionRepository,
                          AnswerRepository answerRepository) {
        answerRepository.deleteAll();
        answerRepository.truncateTable();

        questionRepository.deleteAll();
        questionRepository.truncateTable();

        userRepository.deleteAll();
        userRepository.truncateTable();
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("회원가입이 가능하다.")
    public void t1(){
        userService.create("user", "user@email.com", "1234");
    }
}
