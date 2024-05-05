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

    private void updateScreenElement() {
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

    public static Intent resultIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }
}