package com.example.triviaproject.database.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class QuestionTest {
    private Question question;

    @Before
    public void setUp() {
        question = new Question("What is the capital of France?", "Paris", "Rome", "Berlin", "Paris");
    }

    @Test
    public void getId() {
        question.setId(1);
        assertEquals(1, question.getId());
    }

    @Test
    public void setId() {
        question.setId(2);
        assertEquals(2, question.getId());
    }

    @Test
    public void getQuestionText() {
        assertEquals("What is the capital of France?", question.getQuestionText());
    }

    @Test
    public void setQuestionText() {
        question.setQuestionText("What is the capital of Germany?");
        assertEquals("What is the capital of Germany?", question.getQuestionText());
    }

    @Test
    public void getChoiceA() {
        assertEquals("Paris", question.getChoiceA());
    }

    @Test
    public void setChoiceA() {
        question.setChoiceA("Tokyo");
        assertEquals("Tokyo", question.getChoiceA());
    }

    @Test
    public void getChoiceB() {
        assertEquals("Rome", question.getChoiceB());
    }

    @Test
    public void setChoiceB() {
        question.setChoiceB("Washington D.C.");
        assertEquals("Washington D.C.", question.getChoiceB());
    }

    @Test
    public void getChoiceC() {
        assertEquals("Berlin", question.getChoiceC());
    }

    @Test
    public void setChoiceC() {
        question.setChoiceC("Osaka");
        assertEquals("Osaka", question.getChoiceC());
    }

    @Test
    public void getCorrectChoice() {
        assertEquals("Paris", question.getCorrectChoice());
    }

    @Test
    public void setCorrectChoice() {
        question.setCorrectChoice("Tokyo");
        assertEquals("Tokyo", question.getCorrectChoice());
    }

    @Test
    public void testToString() {
        String expected = "Question{questionText='What is the capital of France?', choiceA='Paris', choiceB='Rome', choiceC='Berlin', correctChoice='Paris'}";
        assertEquals(expected, question.toString());
    }

    @Test
    public void testEquals() {
        Question anotherQuestion = new Question("What is the capital of France?", "Paris", "Rome", "Berlin", "Paris");
        assertEquals(question, anotherQuestion);

        anotherQuestion.setChoiceB("Washington D.C.");
        assertNotEquals(question, anotherQuestion);
    }

    @Test
    public void testHashCode() {
        Question anotherQuestion = new Question("What is the capital of France?", "Paris", "Rome", "Berlin", "Paris");
        assertEquals(question.hashCode(), anotherQuestion.hashCode());
    }
}
