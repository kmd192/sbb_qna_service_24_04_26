package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import com.exam.sbb.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
// @RequiredArgsConstructor 생성자 주입의 한가지 방법
public class QuestionService {

    private final QuestionRepository questionRepository;

    // @Autowired 생략가능
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(("no %d question not found".formatted(id))));
        /*Optional<Question> oq = questionRepository.findById(id);

        if(oq.isPresent()){
            return oq.get();
        }

        throw new DataNotFoundException("question not found");*/
    }

    public void create(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(author);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question){
        questionRepository.delete(question);
    }
}
