package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Question> oq = questionRepository.findById(id);

        if(oq.isPresent()){
            return oq.get();
        }

        throw new DataNotFoundException("question not found");
    }

}
