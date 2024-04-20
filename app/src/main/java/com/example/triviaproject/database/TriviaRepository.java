package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.Trivia;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TriviaRepository {
    private TriviaDAO accountDAO;
    private ArrayList<Trivia> allAccs;
    private static TriviaRepository repository;
    private TriviaRepository(Application application){
        TriviaDatabase db = TriviaDatabase.getDatabase(application);
        this.accountDAO =db.accountDAO();
        this.allAccs = (ArrayList<Trivia>) this.accountDAO.getAllRecords();
    }
    public static TriviaRepository getRepository(Application application){
        if(repository != null){
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
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(RegisterActivity.TAG, "Problem getting account repository");
        }
        return null;
    }
    public ArrayList<Trivia> getAllAccs(){
        Future<ArrayList<Trivia>> future = TriviaDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Trivia>>(){
                    @Override
                    public ArrayList<Trivia> call() throws Exception{
                        return (ArrayList<Trivia>) accountDAO.getAllRecords();
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
    public void insertAccounts(Trivia account){
        TriviaDatabase.databaseWriteExecutor.execute(() ->
        {
            accountDAO.insert(account);
        });
    }
}
