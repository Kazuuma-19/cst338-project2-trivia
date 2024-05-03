package com.example.triviaproject.database;
import androidx.lifecycle.LiveData;
import androidx.room.*;


import com.example.triviaproject.database.entities.Ratio;

import java.util.List;
@Dao
public interface RatioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ratio... ratio);

    @Delete
    void delete(Ratio ratio);

    @Query("SELECT * FROM WLRatio" + " ORDER BY id ASC")
    List<Ratio> getAllRatio();

    @Query("DELETE FROM WLRatio WHERE name = :username")
    void deleteOneRatio(String username);

    @Query("SELECT * FROM " + "WLRatio" + " WHERE name = :username LIMIT 1")
    LiveData<Ratio> getUserByUsername(String username);

    @Query("SELECT * FROM " + "WLRatio" + " WHERE name = :userId")
    LiveData<Ratio> getUserById(int userId);

    @Query("UPDATE WLRatio SET name = :username WHERE id = :userId")
    void updateField(int userId, String username);

    @Query("UPDATE WLRatio SET wins = wins + 1 WHERE name = :name")
    void incrementWins(String name);

    @Query("UPDATE WLRatio SET losses = losses + 1 WHERE name = :name")
    void incrementLosses(String name);

    @Query("SELECT COUNT(*) FROM WLRatio WHERE name = :username")
    LiveData<Integer> getRatioCountByUsername(String username);

}
