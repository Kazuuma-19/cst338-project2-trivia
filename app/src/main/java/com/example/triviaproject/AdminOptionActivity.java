package com.example.triviaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.triviaproject.databinding.ActivityAdminOptionBinding;

public class AdminOptionActivity extends AppCompatActivity {
    private ActivityAdminOptionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminOptionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.createQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreateQuestionActivity.CreateQuestionIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent adminIntentFactory(Context context) {
        return new Intent(context, AdminOptionActivity.class);
    }
}