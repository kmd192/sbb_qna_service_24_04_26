package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import com.exam.sbb.answer.AnswerForm;
import com.exam.sbb.user.SiteUser;
import com.exam.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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
    private final UserService userService;

    @GetMapping("/list")
    //이자리에 @ResponseBody가 없으면 resources/templates/question_list.html을 뷰로 삼는다.
      public String list(String kw, String sortCode, Model model, @RequestParam(value = "page", defaultValue = "0") int page){
          Page<Question> paging = questionService.getList(kw, page);
          model.addAttribute("paging", paging);
          model.addAttribute("kw", kw);
          model.addAttribute("sortCode", sortCode);
          return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id, AnswerForm answerForm){
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);
        //model.addAttribute("answerForm", new AnswerForm(""));
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(Principal principal, @Valid QuestionForm questionForm, BindingResult bindingResult){
        /*boolean hasError = false;

        if(questionForm.getSubject() == null || questionForm.getSubject().trim().length() == 0){
            model.addAttribute("subjectErrorMsg", "제목을 입력해주세요");
            hasError = true;
        }

        if(questionForm.getContent() == null || questionForm.getContent().trim().length() == 0){
            model.addAttribute("contentErrorMsg", "내용을 입력해주세요");
            hasError = true;
        }

        if(hasError){
            model.addAttribute("questionForm", questionForm);
            return "question_form";
        }*/
        if(bindingResult.hasErrors()){
            return "question_form";
        }

        SiteUser author = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), author);

        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") long id, Principal principal){

        Question question = questionService.getQuestion(id);
        if(question == null){
            throw new DataNotFoundException("%d번 질문은 존재하지 않습니다.".formatted(id));
        }

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable("id") long id, Principal principal){

        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());

        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") long id){
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable long id){
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
