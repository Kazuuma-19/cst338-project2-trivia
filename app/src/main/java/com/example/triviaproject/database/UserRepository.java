package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private UserDAO accountDAO;
    private ArrayList<User> allAccs;
    private static UserRepository repository;

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.accountDAO = db.accountDAO();
        this.allAccs = (ArrayList<User>) this.accountDAO.getAllRecords();
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
                        return (ArrayList<User>) accountDAO.getAllRecords();
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

    public void insertAccounts(User account) {
        UserDatabase.databaseWriteExecutor.execute(() ->
        {
            accountDAO.insert(account);
        });
    }
}
