package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private TriviaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = TriviaRepository.getRepository(getApplication());
        binding.loginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.loginUsernameInput.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username cannot be empty");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);
        // Observe the user and wait for the user to be returned
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.loginPasswordInput.getText().toString();

                // Check if the loginPasswordInput is correct
                if (password.equals(user.getPassword())) {
                    Intent intent = LandingActivity.landingIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                } else {
                    toastMaker("Invalid Password");
                    binding.loginPasswordInput.setSelection(0);
                }
            } else {
                // User not found
                toastMaker(String.format(
                        "%s is not a valid username", username
                ));
                binding.loginUsernameInput.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
