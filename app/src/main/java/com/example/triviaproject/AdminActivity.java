package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.adminUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserActivity.userIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.adminGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingActivity.landingIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.adminQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminOptionActivity.adminIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent adminIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }
}