package com.exam.sbb;

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
class QuestionRepositoryTests {

	@Autowired
	private QuestionRepository questionRepository;
	private static int lastSampleDataId;

	@BeforeEach
	void beforeEach(){
		clearData();
		createSampleDate();
	}

	private void clearData() {
		questionRepository.disableForeginKeyChecks();
		questionRepository.truncate();
		questionRepository.enableForeginKeyChecks();
	}

	private void createSampleDate() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		lastSampleDataId = q2.getId();
	}

	@Test
	void 저장(){
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		assertThat(q1.getId()).isEqualTo(lastSampleDataId + 1);
		assertThat(q2.getId()).isEqualTo(lastSampleDataId + 2);
	}

	@Test
	void 삭제() {
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
		Question q = questionRepository.findById(1).get();
		questionRepository.delete(q);
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1);
	}

	@Test
	void 수정() {
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
		Question q = questionRepository.findById(1).get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);

		q = questionRepository.findById(1).get();
		assertThat(q.getSubject()).isEqualTo("수정된 제목");
	}

	@Test
	void findAll() {
		// SELECT * FROM question
		List<Question> all = questionRepository.findAll();
		assertThat(all.size()).isEqualTo(lastSampleDataId);

		Question q = all.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	void findBySubjectLike() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	/*@Test
	void testJpa0(){
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		questionRepository.disableForeginKeyChecks();
		questionRepository.truncate();
		questionRepository.enableForeginKeyChecks();
	}

	@Test
	void testJpa1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		assertThat(q1.getId()).isGreaterThan(0);
		assertThat(q2.getId()).isGreaterThan(q1.getId());
	}

	@Test
	void testJpa2() {
		// SELECT * FROM question
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa3() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		System.out.println(q);//해당 subject 없으면 null인데 exception 발생안하네?
	}

	@Test
	void testJpa4() {
		Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1,q.getId());
	}

	@Test
	void testJpa5() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa6() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testJpa7() {
		assertEquals(2, questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		assertEquals(1, questionRepository.count());
	}*/

}
