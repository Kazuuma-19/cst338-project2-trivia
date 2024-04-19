package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.trivia;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class triviaRepository {
    private triviaDAO accountDAO;
    private ArrayList<trivia> allAccs;
    private static triviaRepository repository;
    private triviaRepository(Application application){
        triviaDatabase db = triviaDatabase.getDatabase(application);
        this.accountDAO =db.accountDAO();
        this.allAccs = (ArrayList<trivia>) this.accountDAO.getAllRecords();
    }
    public static triviaRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<triviaRepository> future = triviaDatabase.databaseWriteExecutor.submit(
                new Callable<triviaRepository>() {
                    @Override
                    public triviaRepository call() throws Exception {
                        return new triviaRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(RegisterActivity.TAG, "Problem getting account repository");
        }
        return null;
    }
    public ArrayList<trivia> getAllAccs(){
        Future<ArrayList<trivia>> future = triviaDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<trivia>>(){
                    @Override
                    public ArrayList<trivia> call() throws Exception{
                        return (ArrayList<trivia>) accountDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(RegisterActivity.TAG, "Problem with getting account repository");
        }
        return null;
    }
    public void insertAccounts(trivia account){
        triviaDatabase.databaseWriteExecutor.execute(() ->
        {
            accountDAO.insert(account);
        });
    }
}
