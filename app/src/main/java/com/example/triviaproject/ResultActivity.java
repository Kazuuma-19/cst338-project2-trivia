package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    private static final String USER_ID = "com.example.triviaproject.USER_ID";
    private static final String CURRENT_CORRECTS = "com.example.triviaproject.CURRENT_CORRECTS";
    private static final String CURRENT_WRONGS = "com.example.triviaproject.CURRENT_WRONGS";
    private ActivityResultBinding binding;
    private TriviaRepository repository;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TriviaRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(USER_ID, -1);

        updateScreenElement();

        binding.resultPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.landingIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void updateCurrentScore() {
        int currentCorrects = getIntent().getIntExtra(CURRENT_CORRECTS, 0);
        int currentWrongs = getIntent().getIntExtra(CURRENT_WRONGS, 0);
        String currentCorrectsText = "Corrects: " + currentCorrects;
        String currentWrongsText = "Wrongs: " + currentWrongs;
        binding.currentWins.setText(currentCorrectsText);
        binding.currentLosses.setText(currentWrongsText);
    }

    private void updateScreenElement() {
        updateCurrentScore();
        // Set the total score
        LiveData<Integer> winsLiveData = repository.getWinsByUserId(userId);
        winsLiveData.observe(this, wins -> {
            String winsText = "Corrects: " + wins;
            binding.wins.setText(winsText);
        });
        LiveData<Integer> lossesLiveData = repository.getLossesByUserId(userId);
        lossesLiveData.observe(this, losses -> {
            String lossesText = "Wrongs: " + losses;
            binding.losses.setText(lossesText);
        });
    }

    public static Intent resultIntentFactory(Context context, int userId, int corrects, int wrongs) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(USER_ID, userId);
        intent.putExtra(CURRENT_CORRECTS, corrects);
        intent.putExtra(CURRENT_WRONGS, wrongs);
        return intent;
    }
}