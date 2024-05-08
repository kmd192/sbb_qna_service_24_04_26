package com.exam.sbb.question;

import com.exam.sbb.RepositoryUtil.RepositoryUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, RepositoryUtil {

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate();

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findBySubjectContains(String kw, Pageable pageable);

    Page<Question> findBySubjectContainsOrContentContains(String kw, String kw2, Pageable pageable);

    Page<Question> findBySubjectContainsOrContentContainsOrAuthor_usernameContains
            (String kw, String kw2, String kw3, Pageable pageable);

    Page<Question> findBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContains
            (String kw, String kw2, String kw3, String kw4, Pageable pageable);

    Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_Author_usernameContains
            (String kw, String kw2, String kw3, String kw4, String kw5, Pageable pageable);
}



