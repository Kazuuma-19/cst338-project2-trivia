package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.resultPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.landingIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent resultIntentFactory(Context context) {
        return new Intent(context, ResultActivity.class);
    }
}