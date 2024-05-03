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

    @Query("SELECT * FROM " + TriviaDatabase.userTable + " ORDER BY id ASC")
    List<User> getAllUsers();

    @Query("DELETE FROM " + TriviaDatabase.userTable)
    void deleteAll();

    @Query("SELECT * FROM " + TriviaDatabase.userTable + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + TriviaDatabase.userTable + " WHERE id = :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("SELECT * FROM " + TriviaDatabase.userTable + " ORDER BY id ASC")
    LiveData<List<User>> getAllUsersLiveData();

    @Query("SELECT COUNT(*) FROM " + TriviaDatabase.userTable + " WHERE username = :username")
    int countUsersByName(String username);//use this for duplication check on register
}
