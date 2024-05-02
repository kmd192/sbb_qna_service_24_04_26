package com.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor // 생성자 주입
// 컨트롤러는 Repository가 있는지 몰라야 한다.
// 서비스는 브라우저라는 것이 이 세상에 존재하는지 몰라야 한다.
// 리포지터리는 서비스가 이세상에 있는지 몰라야 한다.
// 서비스는 컨트롤러를 몰라야 한다.
// DB는 리토지터리를 몰라야 한다.
// SPRING DATA JPA는 MySQL을 몰라야 한다.
// 컨트롤러, 서비스, 리포지터리, JPA, 하이버네이트 간에 소통방식은 일방적이고, 폐쇄적이어야 함
// 건너뛰어서 사용하면 안된다.
// SPRIGN DATA JPA(리포지터리) -> JPA -> 하이버네이트 -> JDBC -> MySQL Dirver -> MySQl
public class QuestionController {

    // @Autowired 필드 주입
    private final QuestionService questionService;

    @GetMapping("/list")
    //이자리에 @ResponseBody가 없으면 resources/templates/question_list.html을 뷰로 삼는다.
      public String list(Model model){
          List<Question> questionList = questionService.getList();
          model.addAttribute("questionList", questionList);
          return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id){
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate() {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(String subject, String content){
        questionService.create(subject, content);

        return "redirect:/question/list";
    }
}
