package com.example.triviaproject.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.trivia;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {trivia.class}, version = 1, exportSchema = false)
public abstract class triviaDatabase extends RoomDatabase {
    private static final String databaseName = "trivia_database";
    public static final String triviaTable = "triviaTable";
    private static volatile triviaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static triviaDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), triviaDatabase.class, databaseName).fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(RegisterActivity.TAG,"database created");
        }
    };

    public abstract triviaDAO accountDAO();
}
