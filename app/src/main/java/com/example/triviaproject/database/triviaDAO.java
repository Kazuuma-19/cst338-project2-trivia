package com.example.triviaproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.triviaproject.database.entities.trivia;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface triviaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(trivia account);

    @Query("SELECT * FROM " + triviaDatabase.triviaTable)
    List<trivia> getAllRecords();
}
