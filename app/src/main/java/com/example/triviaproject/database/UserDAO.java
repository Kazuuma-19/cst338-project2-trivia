package com.example.triviaproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.triviaproject.database.entities.User;

import java.util.List;


@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User account);

    @Query("SELECT * FROM " + UserDatabase.triviaTable + " ORDER BY id ASC")
    List<User> getAllRecords();
}
