package com.example.triviaproject.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.triviaproject.database.UserDatabase;

import java.util.Objects;

@Entity(tableName = UserDatabase.triviaTable)
public class User {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "userName: " + userName + '\n' +
                "password: " + password + '\n' +
                "=-=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User trivia = (User) o;
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
