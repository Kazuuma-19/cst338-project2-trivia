package com.example.triviaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
                    LiveData<Ratio> existingRatioData = repository.getRatioByName(name);
                    existingRatioData.observe(this, existingRatio -> {
                        if (existingRatio == null) {
                            Ratio newRatio = new Ratio(0, 0, name);
                            repository.insertRatio(newRatio);
                        } else {
                            Toast.makeText(this, "Ratio already exists for this user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


        loadQuestion(questionId);

        binding.submit.setOnClickListener(v -> {
            updateRatio();
            updateScreenElement();
            loadQuestion(++questionId);
        });
    }
    private void loadQuestion(int questionId) {
        LiveData<Question> questionLiveData = repository.getQuestionId(questionId);
        questionLiveData.observe(this, question -> {
            if (question != null) {
                binding.questionTitle.setText(question.getQuestionText());
                binding.choiceA.setText(question.getChoiceA());
                binding.choiceB.setText(question.getChoiceB());
                binding.choiceC.setText(question.getChoiceC());
            } else {
                NoMoreQuestions();
            }
        });
    }
    private void NoMoreQuestions() {
        Toast.makeText(this, "No more questions available", Toast.LENGTH_SHORT).show();
    }

    private void updateRatio() {
        LiveData<Question> questionLiveData = repository.getQuestionId(questionId);
        questionLiveData.observe(this, question -> {
            if (question != null) {
                String correctAnswerLetter = question.getCorrectChoice();
                String selectedAnswer = getSelectedAnswer();
                if (selectedAnswer != null && selectedAnswer.equals(correctAnswerLetter)) {
                    repository.incrementWins(name);
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    repository.incrementLosses(name);
                    Toast.makeText(this, "Wrong.", Toast.LENGTH_SHORT).show();
                }
                binding.choiceA.setChecked(false);
                binding.choiceB.setChecked(false);
                binding.choiceC.setChecked(false);
            }
        });
    }

    private void updateScreenElement(){
        LiveData<Integer> winsLiveData = repository.getWinsByUsername(name);
        winsLiveData.observe(this, wins -> {
            String winsText = "Corrects: " + wins;
            binding.Corrects.setText(winsText);
        });
        LiveData<Integer> lossesLiveData = repository.getLossesByUsername(name);
        lossesLiveData.observe(this, losses -> {
            String lossesText = "Wrongs: " + losses;
            binding.Wrongs.setText(lossesText);
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

    public static Intent gameIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, PlayGameActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }
}