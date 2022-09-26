package com.example.skyprosecondcoursework.controller;

import com.example.skyprosecondcoursework.model.Question;
import com.example.skyprosecondcoursework.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/java")
public class JavaQuestionController {

    private final QuestionService questionService;

    public JavaQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam String question,
                                @RequestParam String answer){
        return questionService.add(question, answer);
    }

    @GetMapping("/remove")
    public Question removeQuestion(@RequestParam String question,
                                @RequestParam String answer){
        return questionService.remove(new Question(question, answer));
    }

    @GetMapping("/getAll")
    public Collection<Question> getAll(){
        return questionService.getAll();
    }
}
