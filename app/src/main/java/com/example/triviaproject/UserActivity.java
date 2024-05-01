package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityUserBinding;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    private TriviaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TriviaRepository.getRepository(getApplication());
        binding.userListTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();

        binding.userDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.adminIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void updateDisplay() {
        ArrayList<User> allUsers = repository.getAllUsers();
        if (allUsers.isEmpty()) {
            binding.userListTextView.setText(R.string.no_accounts_found);
        }

        StringBuilder sb = new StringBuilder();
        for (User user : allUsers) {
            sb.append(user);
        }
        binding.userListTextView.setText(sb.toString());
    }

    public static Intent userIntentFactory(Context context) {
        return new Intent(context, UserActivity.class);
    }
}