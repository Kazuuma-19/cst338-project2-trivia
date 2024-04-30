package com.example.triviaproject.database.entities;
import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Objects;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String correctChoice;

    public Question(String questionText, String choiceA, String choiceB, String choiceC, String correctChoice) {
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.correctChoice = correctChoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", choiceA='" + choiceA + '\'' +
                ", choiceB='" + choiceB + '\'' +
                ", choiceC='" + choiceC + '\'' +
                ", correctChoice='" + correctChoice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id && Objects.equals(questionText, question.questionText) && Objects.equals(choiceA, question.choiceA) && Objects.equals(choiceB, question.choiceB) && Objects.equals(choiceC, question.choiceC) && Objects.equals(correctChoice, question.correctChoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, choiceA, choiceB, choiceC, correctChoice);
    }
}
