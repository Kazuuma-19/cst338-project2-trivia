package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.Question;
import com.example.triviaproject.databinding.ActivityCreateQuestionBinding;

public class CreateQuestionActivity extends AppCompatActivity {
    private ActivityCreateQuestionBinding binding;
    private TriviaRepository questionRepository;
    String question = "", choiceA = "", choiceB = "", choiceC = "", correctChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        questionRepository = TriviaRepository.getRepository(getApplication());

        binding.createQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestionInfo();
                saveQuestion();
            }
        });
        binding.questionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminActivity.adminIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void saveQuestion() {
        if (question.isEmpty()) {
            Toast.makeText(this, "There's no question in there", Toast.LENGTH_SHORT).show();
        } else if (choiceA.isEmpty() || choiceB.isEmpty() || choiceC.isEmpty()) {
            Toast.makeText(this, "There's no choice in there", Toast.LENGTH_SHORT).show();
        } else if (correctChoice.isEmpty()) {
            Toast.makeText(this, "There's no correct choice in there", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Question saved", Toast.LENGTH_SHORT).show();
            Question newQuestion = new Question(question, choiceA, choiceB, choiceC, correctChoice);
            questionRepository.insertQuestions(newQuestion);
            cleanUp();
        }
    }

    private void cleanUp() {
        binding.editQuestion.setText("");
        binding.A.setText("");
        binding.B.setText("");
        binding.C.setText("");
        binding.correctQuestion.setText("");
    }

    public static Intent CreateQuestionIntentFactory(Context context) {
        return new Intent(context, CreateQuestionActivity.class);
    }

    private void getQuestionInfo() {
        question = binding.editQuestion.getText().toString();
        choiceA = binding.A.getText().toString();
        choiceB = binding.B.getText().toString();
        choiceC = binding.C.getText().toString();
        correctChoice = binding.correctQuestion.getText().toString();
    }
}