package com.exam.sbb.question;

import org.springframework.stereotype.Service;

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
}
