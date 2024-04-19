package com.example.triviaproject.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.triviaproject.database.triviaDatabase;

import java.util.Objects;

@Entity(tableName = triviaDatabase.triviaTable)
public class trivia {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String userName;
    private String password;

    public trivia(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "trivia{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        trivia trivia = (trivia) o;
        return id == trivia.id && Objects.equals(userName, trivia.userName) && Objects.equals(password, trivia.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
