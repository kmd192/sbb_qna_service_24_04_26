package com.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/sbb")
    @ResponseBody
    public String showHome() {
        return "안녕하세요.";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }
}
