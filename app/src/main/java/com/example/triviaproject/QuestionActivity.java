package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.databinding.ActivityQuestionBinding;

public class QuestionActivity extends AppCompatActivity {
    private ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.createQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateQuestionActivity.CreateQuestionIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.deleteQuestions.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteQuestionsActivity.DeleteQuestionIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        }));
    }

    public static Intent questionIntentFactory(Context context) {
        return new Intent(context, QuestionActivity.class);
    }
}