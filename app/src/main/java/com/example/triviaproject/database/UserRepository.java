package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private final UserDAO userDAO;
    private ArrayList<User> allAccs;
    private static UserRepository repository;

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allAccs = (ArrayList<User>) this.userDAO.getAllRecords();
    }

    public static UserRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<UserRepository> future = UserDatabase.databaseWriteExecutor.submit(
                new Callable<UserRepository>() {
                    @Override
                    public UserRepository call() throws Exception {
                        return new UserRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(RegisterActivity.TAG, "Problem getting account repository");
        }
        return null;
    }

    public ArrayList<User> getAllAccounts() {
        Future<ArrayList<User>> future = UserDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(RegisterActivity.TAG, "Problem with getting account repository");
        }
        return null;
    }

    public void insertAccounts(User... user) {
        UserDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int loggedInUserId) {
        return userDAO.getUserByUserId(loggedInUserId);
    }
}
