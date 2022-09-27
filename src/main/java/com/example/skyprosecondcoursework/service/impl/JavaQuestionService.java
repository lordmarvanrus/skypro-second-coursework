package com.example.skyprosecondcoursework.service.impl;

import com.example.skyprosecondcoursework.exception.QuestionAlreadyExistException;
import com.example.skyprosecondcoursework.exception.QuestionNonFoundException;
import com.example.skyprosecondcoursework.model.Question;
import com.example.skyprosecondcoursework.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions;
    private final Random random;

    public JavaQuestionService() {
        this.questions = new HashSet<>();
        this.random = new Random();
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if(!questions.add(question)){
            throw new QuestionAlreadyExistException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if(!questions.remove(question)){
            throw new QuestionNonFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableCollection(new HashSet<>(questions));
    }

    @Override
    public Question getRandomQuestion() {
        return new ArrayList<>(questions).get(random.nextInt(questions.size()));
    }
}
