package com.exam.sbb;

import com.exam.sbb.answer.Answer;
import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class AnswerRepositoryTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	private static int lastSampleDataId;

	@BeforeEach
	void beforeEach(){
		clearData();
		createSampleDate();
	}

	private void clearData() {
		QuestionRepositoryTests.clearData(questionRepository);

		questionRepository.disableForeginKeyChecks();
		answerRepository.truncate();
		questionRepository.enableForeginKeyChecks();
	}

	private void createSampleDate() {
		QuestionRepositoryTests.createSampleData(questionRepository);
	}

	@Test
	void testJpa() {
		Question q = questionRepository.findById(2).get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

}