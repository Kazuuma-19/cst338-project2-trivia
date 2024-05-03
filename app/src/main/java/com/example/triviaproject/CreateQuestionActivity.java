package com.example.triviaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                cleanUp();
            }
        });
    }

    private void saveQuestion() {
        if(question.isEmpty()){
            Toast.makeText(this, "There's no question in there", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Question saved", Toast.LENGTH_SHORT).show();
            Question newQuestion = new Question(question, choiceA, choiceB, choiceC, correctChoice);
            questionRepository.insertQuestions(newQuestion);
        }
    }
    
    private void cleanUp(){
        binding.editQuestion.setText("");
        binding.A.setText("");
        binding.B.setText("");
        binding.C.setText("");
        binding.correctQuestion.setText("");
    }

    public static Intent CreateQuestionIntentFactory(Context context) {
        return new Intent(context, CreateQuestionActivity.class);
    }

    private void getQuestionInfo(){
        question = binding.editQuestion.getText().toString();
        choiceA = binding.A.getText().toString();
        choiceB = binding.B.getText().toString();
        choiceC = binding.C.getText().toString();
        correctChoice = binding.correctQuestion.getText().toString();
    }
}