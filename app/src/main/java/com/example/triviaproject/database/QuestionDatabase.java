package com.example.triviaproject.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.Question;
import com.example.triviaproject.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Question.class}, version = 1, exportSchema = false)
public abstract class QuestionDatabase extends RoomDatabase {
    private static volatile QuestionDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuestionDatabase getQuestionDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QuestionDatabase.class, "question_database").addCallback(addDefaultValues).build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("questions", "database created");
        }
    };
    public abstract QuestionDAO questionDao();
}
