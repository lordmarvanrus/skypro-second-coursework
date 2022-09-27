package com.example.skyprosecondcoursework.impl;

import com.example.skyprosecondcoursework.exception.IncorrectAmountOfQuestionsException;
import com.example.skyprosecondcoursework.model.Question;
import com.example.skyprosecondcoursework.service.impl.ExaminerServiceImpl;
import com.example.skyprosecondcoursework.service.impl.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void beforeEach(){
        Collection<Question> questions = Stream.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3"),
                new Question("Q4", "A4"),
                new Question("Q5", "A5")
        ).collect(Collectors.toSet());

        when(javaQuestionService.getAll()).thenReturn(questions);
    }

    @ParameterizedTest
    @MethodSource("negativeParams")
    public void getQuestionNegativeTest(int incorrectAmount){
        assertThatExceptionOfType(IncorrectAmountOfQuestionsException.class)
                .isThrownBy(() -> examinerService.getQuestions(incorrectAmount));
    }

    @Test
    public void getQuestionTest(){
        List<Question> questions = new ArrayList<>(javaQuestionService.getAll());

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(0),
                questions.get(1),
                questions.get(0),
                questions.get(2)
        );

        assertThat(examinerService.getQuestions(3)).containsExactly(questions.get(0), questions.get(1), questions.get(2));

        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(0),
                questions.get(1),
                questions.get(2),
                questions.get(4),
                questions.get(2),
                questions.get(3)
        );

        assertThat(examinerService.getQuestions(5))
                .containsExactly(questions.get(0), questions.get(1), questions.get(2), questions.get(4), questions.get(3));
    }

    public static Stream<Arguments> negativeParams(){
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(0),
                Arguments.of(6),
                Arguments.of(10)
        );
    }
}
