package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private TriviaRepository repository;
    public static final String TAG = "DAC_trivia";
    String uName = "";
    String uPassword = "";
    boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = TriviaRepository.getRepository(getApplication());

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromDisplay();
                insertUserInfo();
                // Jump to the loginSignInButton activity after registering
                Intent loginIntent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(loginIntent);
            }
        });
    }

    private void insertUserInfo() {
        if (uName.isEmpty() || uPassword.isEmpty()) {
            return;
        }
        User user = new User(uName, uPassword, isAdmin);
        repository.insertUsers(user);
    }

    public static Intent registerIntentFactory(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    private void getInfoFromDisplay() {
        uName = binding.loginUsernameInput.getText().toString();
        uPassword = binding.loginPasswordInput.getText().toString();
        isAdmin = binding.adminSwitch.isChecked();
    }
}
