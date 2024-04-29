package com.exam.sbb.question;

import com.exam.sbb.RepositoryUtil.RepositoryUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositoryUtil {

    @Transactional
    @Modifying
    @Query(value = "truncate question", nativeQuery = true)
    void truncate();

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}
