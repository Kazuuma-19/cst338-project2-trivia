package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TriviaRepository {
    private final UserDAO userDAO;
    private ArrayList<User> allUsers;
    private static TriviaRepository repository;

    private TriviaRepository(Application application) {
        TriviaDatabase db = TriviaDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allUsers = (ArrayList<User>) this.userDAO.getAllUsers();
    }

    public static TriviaRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<TriviaRepository> future = TriviaDatabase.databaseWriteExecutor.submit(
                new Callable<TriviaRepository>() {
                    @Override
                    public TriviaRepository call() throws Exception {
                        return new TriviaRepository(application);
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

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = TriviaDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllUsers();
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

    public void insertUsers(User... user) {
        TriviaDatabase.databaseWriteExecutor.execute(() ->
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

    public LiveData<List<User>> getAllUsersLiveData() {
        return userDAO.getAllUsersLiveData();
    }
}
