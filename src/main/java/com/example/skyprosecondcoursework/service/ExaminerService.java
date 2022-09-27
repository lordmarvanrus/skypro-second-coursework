package com.example.skyprosecondcoursework.service;

import com.example.skyprosecondcoursework.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}
