package com.example.triviaproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.UserRepository;
import com.example.triviaproject.database.entities.User;
import com.example.triviaproject.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {
    private static final String LANDING_ACTIVITY_USER_ID = "com.example.triviaproject.LANDING_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.triviaproject.SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.triviaproject.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;
    private ActivityLandingBinding binding;
    int loggedInUserId = -1;
    private User user;
    private UserRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repository = UserRepository.getRepository(getApplication());

        loginUser();

        if (loggedInUserId == -1) {
            // Jump to the login activity if the user is not logged in
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        binding.landingStartGameButton.setOnClickListener(new View.OnClickListener() {
            // Start Game
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * Get the user id from the shared preferences and the intent
     */
    private void loginUser() {
        // Get the user id from the shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        // Get the user id from the intent
        loggedInUserId = getIntent().getIntExtra(LANDING_ACTIVITY_USER_ID, LOGGED_OUT);
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        // Observe the user and wait for the user to be returned
        userObserver.observe(this, user -> {
            if (user != null) {
                this.user = user;
                invalidateOptionsMenu();

                binding.landingUserName.setText(user.getUserName());
                // Add the admin button if the user is an admin
                if (user.isAdmin()) {
                    binding.adminPrivileges.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Create a menu to logout
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Add the user name to the menu
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setTitle(user.getUserName());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });


        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
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

    private void logout() {
        // Set the user id to -1 in the shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        sharedPrefEditor.apply();

        getIntent().putExtra(LANDING_ACTIVITY_USER_ID, LOGGED_OUT);

        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    /**
     * Create an intent to start the landing activity
     *
     * @param context
     * @param userId  it is passed to the landing activity from the another activity
     * @return intent
     */
    public static Intent landingIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(LANDING_ACTIVITY_USER_ID, userId);
        return intent;
    }
}