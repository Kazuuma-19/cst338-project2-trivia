package com.example.triviaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaproject.database.TriviaRepository;
import com.example.triviaproject.databinding.ActivityUserBinding;
import com.example.triviaproject.viewHolders.UserAdapter;
import com.example.triviaproject.viewHolders.UserViewModel;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    private TriviaRepository repository;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Recycler view setup
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RecyclerView recyclerView = binding.userListRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = TriviaRepository.getRepository(getApplication());
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });

        binding.userDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.adminIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent userIntentFactory(Context context) {
        return new Intent(context, UserActivity.class);
    }
}