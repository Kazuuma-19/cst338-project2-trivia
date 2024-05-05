package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.Question;
import com.example.triviaproject.databinding.ActivityDeleteQuestionsBinding;

public class DeleteQuestionsActivity extends AppCompatActivity {
    private ActivityDeleteQuestionsBinding binding;
    private TriviaRepository repository;
    private int questionId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TriviaRepository.getRepository(getApplication());
        loadQuestion(questionId);
        binding.Next.setOnClickListener(v -> {
            loadQuestion(++questionId);
        });
        binding.Prev.setOnClickListener(v -> {
            loadQuestion(--questionId);
        });
        binding.Del.setOnClickListener(v -> {
            deleteQuestion(questionId);
        });
        binding.deleteQuestionDoneButton.setOnClickListener(v -> {
            Intent intent = AdminActivity.adminIntentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    private void loadQuestion(int questionId) {
        LiveData<Question> questionLiveData = repository.getQuestionByQuestionId(questionId);
        questionLiveData.observe(this, question -> {
            if (question != null) {
                binding.questionPromptDelete.setText(question.getQuestionText());
                binding.aDelete.setText(question.getChoiceA());
                binding.bDelete.setText(question.getChoiceB());
                binding.cDelete.setText(question.getChoiceC());
            }
        });
    }

    private void deleteQuestion(int questionId) {
        repository.deleteOneQuestion(questionId);
    }

    public static Intent DeleteQuestionIntentFactory(Context context) {
        return new Intent(context, DeleteQuestionsActivity.class);
    }
}