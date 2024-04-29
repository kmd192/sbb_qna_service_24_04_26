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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnswerRepositoryTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach
	void beforeEach(){
		clearData();
		createSampleDate();
	}

	private void clearData() {
		QuestionRepositoryTests.clearData(questionRepository);
		answerRepository.deleteAll();
		answerRepository.truncateTable();
	}

	private void createSampleDate() {
		QuestionRepositoryTests.createSampleData(questionRepository);

		Question q = questionRepository.findById(1).get();

		Answer a1 = new Answer();
		a1.setContent("sbb는 질문답변 게시판입니다.");
		a1.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a1.setCreateDate(LocalDateTime.now());
		answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
		a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a2.setCreateDate(LocalDateTime.now());
		answerRepository.save(a2);
	}

	@Test
	void 저장() {
		Question q = questionRepository.findById(1).get();

		Answer a1 = new Answer();
		a1.setContent("sbb는 질문답변 게시판입니다.");
		a1.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a1.setCreateDate(LocalDateTime.now());
		answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
		a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a2.setCreateDate(LocalDateTime.now());
		answerRepository.save(a2);
	}

	@Test
	void 조회() {
		Answer a = answerRepository.findById(1).get();
		assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}

	@Test
	void 관련된_question_조회() {
		Answer a = answerRepository.findById(1).get();
		Question q = a.getQuestion();
		assertThat(q.getId()).isEqualTo(1);
	}

	@Test
	void question으로부터_관련된_답변들_조회() {
		Question q = questionRepository.findById(1).get();
		List<Answer> answerList = q.getAnswerList();

		assertThat(answerList.size()).isEqualTo(2);
		assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}

}
