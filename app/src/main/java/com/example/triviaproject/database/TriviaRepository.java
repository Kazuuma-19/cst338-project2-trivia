package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.Question;
import com.example.triviaproject.database.entities.Ratio;
import com.example.triviaproject.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TriviaRepository {
    private final UserDAO userDAO;
    private ArrayList<User> allUsers;
    private static TriviaRepository repository;
    private final QuestionDAO questionDao;
    private ArrayList<Question> allQuestions;
    private static TriviaRepository qRepository;
    private final RatioDAO ratioDao;
    private ArrayList<Ratio> allRatios;
    private static TriviaRepository wlRepository;

    private TriviaRepository(Application application) {
        TriviaDatabase db = TriviaDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allUsers = (ArrayList<User>) this.userDAO.getAllUsers();
        this.questionDao = db.questionDao();
        this.allQuestions = (ArrayList<Question>) this.questionDao.getAllQuestions();
        this.ratioDao = db.ratioDao();
        this.allRatios = (ArrayList<Ratio>) this.ratioDao.getAllRatio();
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

    public ArrayList<Question> getAllQuestions() {
        Future<ArrayList<Question>> future = TriviaDatabase.databaseWriteExecutor.submit(
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



    public ArrayList<Ratio> getAllRatio() {
        Future<ArrayList<Ratio>> future = TriviaDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Ratio>>() {
                    @Override
                    public ArrayList<Ratio> call() throws Exception {
                        return (ArrayList<Ratio>) ratioDao.getAllRatio();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("WLRatio", "Problem with getting scores");
        }
        return null;
    }

    public void insertRatio(Ratio ratio) {
        TriviaDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.insert(ratio);
        });
    }

    public void incrementWins(String name) {
        TriviaDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.incrementWins(name);
        });
    }

    public void incrementLosses(String name) {
        TriviaDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.incrementLosses(name);
        });
    }

    public LiveData<Ratio> getRatioByName(String name) {
        return ratioDao.getUserByUsername(name);
    }

    public LiveData<Integer> getRatioCountByUsername(String username) {
        return ratioDao.getRatioCountByUsername(username);
    }

    public void deleteOneRatio(String username) {
        ratioDao.deleteOneRatio(username);
    }
}
