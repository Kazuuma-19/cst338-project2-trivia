package com.example.triviaproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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
    private static final String USER_ACTIVITY_USER_ID = "com.example.triviaproject.USER_ACTIVITY_USER_ID";
    private int loggedInUserId;
    private static final int LOGGED_OUT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getLoggedInUserId();

        // Recycler view setup
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RecyclerView recyclerView = binding.userListRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });

        repository = TriviaRepository.getRepository(getApplication());

        binding.userDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.adminIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.userDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    private void getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Delete Current Logged In User?");

        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void deleteUser() {
        repository.deleteUserByUserId(loggedInUserId);
        logout();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();

        getIntent().putExtra(USER_ACTIVITY_USER_ID, LOGGED_OUT);

        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    /**
     * Update user id in the shared preferences
     * It stores the data permanently
     */
    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    public static Intent userIntentFactory(Context context) {
        return new Intent(context, UserActivity.class);
    }
}