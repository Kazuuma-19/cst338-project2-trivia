package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaproject.database.entities.Trivia;
import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.databinding.ActivityRegisterBinding;

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
        binding.register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getInfoFromDisplay();
                insertAccountInfo();
                updateDisplay();
            }
        });
    }
    private void updateDisplay(){
        String info = binding.textView.getText().toString();
        Log.d(TAG, "updateDisplay: ");
        String newInfo = String.format("Username:%s%nPassword:%s%n",uName,uPassword,info);
        binding.textView.setText(newInfo);
        Log.i(TAG,repository.getAllAccs().toString());
    }
    private void insertAccountInfo(){
        Trivia acc = new Trivia(uName,uPassword);
        repository.insertAccounts(acc);
    }
    public static Intent registerIntentFactory(Context context){
        return new Intent(context, RegisterActivity.class);
    }
    private void getInfoFromDisplay(){
        uName = binding.username.getText().toString();
        uPassword = binding.password.getText().toString();
    }
}
