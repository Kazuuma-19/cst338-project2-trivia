package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.triviaproject.database.entities.Question;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QuestionRepository {
    private QuestionDAO questionDao;
    private ArrayList<Question> allQuestions;

    private static QuestionRepository qRepository;

    public QuestionRepository(Application application) {
        QuestionDatabase db = QuestionDatabase.getQuestionDatabase(application);
        this.questionDao = db.questionDao();
        this.allQuestions = (ArrayList<Question>) this.questionDao.getAllQuestions();
    }

    public static QuestionRepository getQuestionRepository(Application application) {
        if (qRepository != null) {
            return qRepository;
        }
        Future<QuestionRepository> future = QuestionDatabase.databaseWriteExecutor.submit(
                new Callable<QuestionRepository>() {
                    @Override
                    public QuestionRepository call() throws Exception {
                        return new QuestionRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("questions", "Problem getting question repository");
        }
        return null;
    }

    public ArrayList<Question> getAllQuestions() {
        Future<ArrayList<Question>> future = QuestionDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Question>>() {
                    @Override
                    public ArrayList<Question> call() throws Exception {
                        return (ArrayList<Question>) questionDao.getAllQuestions();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("questions", "Problem with getting question repository");
        }
        return null;
    }

    public void insertQuestions(Question... question) {
        TriviaDatabase.databaseWriteExecutor.execute(() ->
        {
            questionDao.insert(question);
        });
    }

    public LiveData<Question> getQuestionId(int questionId) {
        return questionDao.getQuestionId(questionId);
    }
}
