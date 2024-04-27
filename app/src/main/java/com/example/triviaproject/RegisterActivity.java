package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityRegisterBinding;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private TriviaRepository repository;
    public static final String TAG = "DAC_trivia";
    String uName = "";
    String uPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = TriviaRepository.getRepository(getApplication());
        binding.accountDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromDisplay();
                insertUserInfo();
                updateDisplay();
            }
        });

        binding.loginUsernameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplay();
            }
        });
    }

    private void updateDisplay() {
        ArrayList<User> allUsers = repository.getAllUsers();
        if (allUsers.isEmpty()) {
            binding.accountDisplayTextView.setText(R.string.no_accounts_found);
        }

        StringBuilder sb = new StringBuilder();
        for (User user : allUsers) {
            sb.append(user);
        }
        binding.accountDisplayTextView.setText(sb.toString());
    }

    private void insertUserInfo() {
        if (uName.isEmpty() || uPassword.isEmpty()) {
            return;
        }
        User user = new User(uName, uPassword);
        repository.insertUsers(user);
    }

    public static Intent registerIntentFactory(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    private void getInfoFromDisplay() {
        uName = binding.loginUsernameInput.getText().toString();
        uPassword = binding.loginPasswordInput.getText().toString();
    }
}
