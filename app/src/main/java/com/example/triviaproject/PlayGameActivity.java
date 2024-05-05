package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.Question;
import com.example.triviaproject.database.entities.Ratio;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityPlayGameBinding;

public class PlayGameActivity extends AppCompatActivity {
    private static final String USER_ID = "com.example.triviaproject.USER_ID";
    private ActivityPlayGameBinding binding;
    private TriviaRepository repository;
    private int userId = -1;
    private int questionId = 1;
    private String name = "";
    private int corrects = 0;
    private int wrongs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TriviaRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(USER_ID, -1);

        if (userId != -1) {
            LiveData<User> userLiveData = repository.getUserByUserId(userId);
            userLiveData.observe(this, user -> {
                if (user != null) {
                    name = user.getUserName();
                    // Check if the database already has a row with the same username
                    LiveData<Ratio> existingRatioLiveData = repository.getRatioByName(name);
                    existingRatioLiveData.observe(this, existingRatio -> {
                        if (existingRatio == null) {
                            // No existing ratio found, so insert a new one
                            Ratio newRatio = new Ratio(0, 0, name);
                            repository.insertRatio(newRatio);
                        }
                    });
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                }
            });
        }

        loadQuestion(questionId);

        binding.submit.setOnClickListener(v -> {
            updateRatio();
            loadQuestion(++questionId);
        });
    }

    private void updateRatio() {
        LiveData<Question> questionLiveData = repository.getQuestionByQuestionId(questionId);
        questionLiveData.observe(this, question -> {
            if (question != null) {
                String correctAnswerLetter = question.getCorrectChoice();
                String selectedAnswer = getSelectedAnswer();
                if (selectedAnswer != null && selectedAnswer.equals(correctAnswerLetter)) {
                    repository.incrementWins(name);
                    corrects++;
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    repository.incrementLosses(name);
                    wrongs++;
                    Toast.makeText(this, "Wrong.", Toast.LENGTH_SHORT).show();
                }
                binding.choiceA.setChecked(false);
                binding.choiceB.setChecked(false);
                binding.choiceC.setChecked(false);
            }
        });
    }

    private void loadQuestion(int questionId) {
        LiveData<Question> questionLiveData = repository.getQuestionByQuestionId(questionId);
        questionLiveData.observe(this, question -> {
            if (question != null) {
                binding.questionTitle.setText(question.getQuestionText());
                binding.choiceA.setText(question.getChoiceA());
                binding.choiceB.setText(question.getChoiceB());
                binding.choiceC.setText(question.getChoiceC());
            } else {
                Intent intent = ResultActivity.resultIntentFactory(getApplicationContext(), userId, corrects, wrongs);
                startActivity(intent);
            }
        });
    }

    private String getSelectedAnswer() {
        if (binding.choiceA.isChecked()) {
            return "A";
        } else if (binding.choiceB.isChecked()) {
            return "B";
        } else if (binding.choiceC.isChecked()) {
            return "C";
        } else {
            return null;
        }
    }

    public static Intent gameIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, PlayGameActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }
}