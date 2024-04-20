package com.example.triviaproject.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.triviaproject.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + UserDatabase.triviaTable + " ORDER BY id ASC")
    List<User> getAllRecords();

    @Query("DELETE FROM " + UserDatabase.triviaTable)
    void deleteAll();

    @Query("SELECT * FROM " + UserDatabase.triviaTable + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + UserDatabase.triviaTable + " WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);
}
