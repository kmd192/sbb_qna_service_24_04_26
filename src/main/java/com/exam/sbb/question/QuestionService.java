package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
// @RequiredArgsConstructor 생성자 주입의 한가지 방법
public class QuestionService {

    private final QuestionRepository questionRepository;

    // @Autowired 생략가능
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(("no %d question not found".formatted(id))));
        /*Optional<Question> oq = questionRepository.findById(id);

        if(oq.isPresent()){
            return oq.get();
        }

        throw new DataNotFoundException("question not found");*/
    }

    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }
}
