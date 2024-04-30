package com.exam.sbb.controller;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입
public class QuestionController {

    // @Autowired 필드 주입
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    //이자리에 @ResponseBody가 없으면 resources/templates/question_list.html을 뷰로 삼는다.
    public String list(Model model){
        List<Question> questionList = questionRepository.findAll();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
}
