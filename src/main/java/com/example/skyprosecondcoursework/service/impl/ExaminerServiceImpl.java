package com.example.skyprosecondcoursework.service.impl;

import com.example.skyprosecondcoursework.exception.IncorrectAmountOfQuestionsException;
import com.example.skyprosecondcoursework.model.Question;
import com.example.skyprosecondcoursework.service.ExaminerService;
import com.example.skyprosecondcoursework.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount){
        if(amount > questionService.getAll().size() || amount <= 0){
            throw new IncorrectAmountOfQuestionsException();
        }
        Set<Question> questions = new HashSet<>();
        while (questions.size() < amount) {
            questions.add(questionService.getRandomQuestion());
        }
        return questions;
    }
}
