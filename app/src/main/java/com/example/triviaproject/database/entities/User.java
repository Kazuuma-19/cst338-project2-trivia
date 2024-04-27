package com.example.triviaproject.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.triviaproject.database.TriviaDatabase;

import java.util.Objects;

@Entity(tableName = TriviaDatabase.userTable)
public class User {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String userName;
    private String password;
    private boolean isAdmin;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = false;
    }

    @NonNull
    @Override
    public String toString() {
        return "userName: " + userName + '\n' +
                "password: " + password + '\n' +
                "isAdmin: " + isAdmin + '\n' +
                "=-=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, isAdmin);
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
