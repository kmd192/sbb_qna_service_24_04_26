package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionService;
import com.exam.sbb.user.SiteUser;
import com.exam.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PostMapping("/create/{id}")
    public String createAnswer(Principal principal, Model model, @PathVariable Long id, @Valid AnswerForm answerForm, BindingResult bindingResult){

        Question question = questionService.getQuestion(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }

        SiteUser author = userService.getUser(principal.getName());

        answerService.create(question, answerForm.getContent(), author);

        return String.format("redirect:/question/detail/%d", id);
    }
}
