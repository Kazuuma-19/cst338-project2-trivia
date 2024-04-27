package com.example.triviaproject.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.triviaproject.RegisterActivity;
import com.example.triviaproject.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TriviaDatabase extends RoomDatabase {
    private static final String databaseName = "trivia_database";
    public static final String userTable = "userTable";
    private static volatile TriviaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TriviaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TriviaDatabase.class, databaseName).fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(RegisterActivity.TAG, "database created");
            // Add default users to the database
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract UserDAO userDAO();
}
