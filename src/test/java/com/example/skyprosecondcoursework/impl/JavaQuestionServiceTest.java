package com.example.skyprosecondcoursework.impl;

import com.example.skyprosecondcoursework.exception.QuestionAlreadyExistException;
import com.example.skyprosecondcoursework.exception.QuestionNonFoundException;
import com.example.skyprosecondcoursework.model.Question;
import com.example.skyprosecondcoursework.service.QuestionService;
import com.example.skyprosecondcoursework.service.impl.JavaQuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void afterEach(){
        Collection<Question> questions = questionService.getAll();
        questions.forEach(questionService::remove);
    }

    @Test
    public void addTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected1 = new Question("Q1", "A1");
        Question expected2 = new Question("Q2", "A2");
        questionService.add(expected1);
        questionService.add(expected2.getQuestion(), expected2.getAnswer());
        assertThat(questionService.getAll()).hasSize(2);
        assertThat(questionService.getAll()).contains(expected1, expected2);
    }

    @Test
    public void addNegativeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question expected = addOneQuestion();

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> questionService.add(expected));

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> questionService.add(expected.getQuestion(), expected.getAnswer()));

    }

    @Test
    public void removeTest(){
        assertThat(questionService.getAll()).isEmpty();

        Question expected = addOneQuestion();

        assertThatExceptionOfType(QuestionNonFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Q2", "A2")));

        questionService.remove(expected);
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    public void getRandomQuestionTest(){
        assertThat(questionService.getAll()).isEmpty();

        int size = 5;
        for (int i = 0; i < 5; i++) {
            addOneQuestion("Q" + i, "A" + i);
        }

        assertThat(questionService.getAll()).hasSize(size);
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }

    private Question addOneQuestion(String question, String answer){
        int size = questionService.getAll().size();

        Question expected = new Question(question, answer);
        questionService.add(expected);

        assertThat(questionService.getAll()).hasSize(size + 1);
        assertThat(questionService.getAll()).contains(expected);

        return expected;
    }

    private Question addOneQuestion(){
        return addOneQuestion("Q1", "A1");
    }

}
