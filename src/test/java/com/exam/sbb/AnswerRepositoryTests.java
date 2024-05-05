package com.exam.sbb;

import com.exam.sbb.answer.Answer;
import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import com.exam.sbb.user.SiteUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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
		createSampleData();
	}

	private void clearData(){
		clearData(questionRepository, answerRepository);
	}

	public static void clearData(QuestionRepository questionRepository, AnswerRepository answerRepository) {
		answerRepository.deleteAll();
		answerRepository.truncateTable();
		QuestionRepositoryTests.clearData(questionRepository);
	}

	private void createSampleData(){
		createSampleData(questionRepository, answerRepository);
	}

	public static void createSampleData(QuestionRepository questionRepository, AnswerRepository answerRepository) {
		QuestionRepositoryTests.createSampleData(questionRepository);

		Question q = questionRepository.findById(1L).get();

		Answer a1 = new Answer();
		a1.setContent("sbb는 질문답변 게시판입니다.");
		a1.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a1.setAuthor(new SiteUser(1L));
		a1.setCreateDate(LocalDateTime.now());
		answerRepository.save(a1);

		q.getAnswerList().add(a1);

		Answer a2 = new Answer();
		a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
		a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a2.setAuthor(new SiteUser(2L));
		a2.setCreateDate(LocalDateTime.now());
		answerRepository.save(a2);

		q.getAnswerList().add(a2);
	}

	@Test
	@Transactional
	@Rollback(false)
	void 저장() {
		Question q = questionRepository.findById(2L).get();

		Answer a1 = new Answer();
		a1.setContent("네 자동으로 생성됩니다.");
		a1.setCreateDate(LocalDateTime.now());
		q.addAnswer(a1);

		Answer a2 = new Answer();
		a2.setContent("네~ 맞아요!");
		a2.setCreateDate(LocalDateTime.now());
		q.addAnswer(a2);

		questionRepository.save(q);
	}

	@Test
	@Transactional
	@Rollback(false)
	void 조회() {
		Answer a = answerRepository.findById(1L).get();
		assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}

	@Test
	@Transactional
	@Rollback(false)
	void 관련된_question_조회() {
		Answer a = answerRepository.findById(1L).get();
		Question q = a.getQuestion();

		assertThat(q.getId()).isEqualTo(1);
	}

	@Test
	@Transactional
	@Rollback(false)
	void question으로부터_관련된_답변들_조회() {
		//beforeEach를 변경하기 전에 1차캐시,영속성 컨텍스트의 값이었었음-> Question q = questionRepository.findById(1).get();
		Question q = questionRepository.findById(1L).get();
		List<Answer> answerList = q.getAnswerList();

		assertThat(answerList.size()).isEqualTo(2);
		assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}

}
