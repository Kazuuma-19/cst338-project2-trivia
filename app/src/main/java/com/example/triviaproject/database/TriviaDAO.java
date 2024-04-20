package com.example.triviaproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.triviaproject.database.entities.Trivia;

import java.util.List;


@Dao
public interface TriviaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trivia account);

    @Query("SELECT * FROM " + TriviaDatabase.triviaTable)
    List<Trivia> getAllRecords();
}
