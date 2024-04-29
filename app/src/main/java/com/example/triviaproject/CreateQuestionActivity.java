package com.example.triviaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class CreateQuestionActivity extends AppCompatActivity {

    EditText question;
    Button create, promptA, promptB, promptC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        question = findViewById(R.id.editQuestion);
        promptA = findViewById(R.id.A);
        promptB = findViewById(R.id.B);
        promptC = findViewById(R.id.C);
        create = findViewById(R.id.createQuestion);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionText = question.getText().toString();
                String promptAText = promptA.getText().toString();
                String promptBText = promptB.getText().toString();
                String promptCText = promptC.getText().toString();
                if (!questionText.isEmpty()) {
                    saveQuestion(questionText);
                    Toast.makeText(CreateQuestionActivity.this, "Question saved!", Toast.LENGTH_SHORT).show();
                    question.setText("");
                } else {
                    Toast.makeText(CreateQuestionActivity.this, "Please enter a question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveQuestion(String questionText) {
        // Implement saving mechanism here
        // For example, save to a database or send to another activity
    }

    public static Intent CreateQuestionIntentFactory(Context context) {
        return new Intent(context, CreateQuestionActivity.class);
    }
}