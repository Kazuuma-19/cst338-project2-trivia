package com.example.triviaproject.database;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.triviaproject.database.entities.Question;

import java.util.List;
@Dao
public interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question... question);

    @Delete
    void delete(Question question);

    @Query("SELECT * FROM questions" + " ORDER BY id ASC")
    List<Question> getAllQuestions();

    @Query("DELETE FROM " + "questions")
    void deleteAll();

    @Query("SELECT * FROM " + "questions" + " WHERE id = :questionId")
    LiveData<Question> getQuestionId(int questionId);

    @Query("DELETE FROM questions WHERE id = :questionId")
    void deleteOneQuestion(int questionId);
}
