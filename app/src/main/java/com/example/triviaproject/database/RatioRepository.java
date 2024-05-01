package com.example.triviaproject.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.triviaproject.database.entities.Ratio;


import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RatioRepository {
    private final RatioDAO ratioDao;
    private ArrayList<Ratio> allRatios;
    private static RatioRepository wlRepository;
    private RatioRepository(Application application) {
        RatioDatabase db = RatioDatabase.getRatioDatabase(application);
        this.ratioDao = db.ratioDao();
        this.allRatios = (ArrayList<Ratio>) this.ratioDao.getAllRatio();
    }

    public static RatioRepository getRatioRepository(Application application) {
        if (wlRepository != null) {
            return wlRepository;
        }
        Future<RatioRepository> future = RatioDatabase.databaseWriteExecutor.submit(
                new Callable<RatioRepository>() {
                    @Override
                    public RatioRepository call() throws Exception {
                        return new RatioRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("W/L ratio", "Problem getting score board");
        }
        return null;
    }

    public void insertRatio(Ratio ratio) {
        RatioDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.insert(ratio);
        });
    }

    public void incrementWins(String name) {
        RatioDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.incrementWins(name);
        });
    }

    public void incrementLosses(String name) {
        RatioDatabase.databaseWriteExecutor.execute(() -> {
            ratioDao.incrementLosses(name);
        });
    }

    public LiveData<Ratio> getRatioByName(String name) {
        return ratioDao.getUserByUsername(name);
    }

}
