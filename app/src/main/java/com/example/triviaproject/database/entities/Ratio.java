package com.example.triviaproject.database.entities;
import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Objects;
@Entity(tableName = "WLRatio")
public class Ratio {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int wins;
    private int losses;
    private String name;

    public Ratio(int wins, int losses, String name) {
        this.wins = wins;
        this.losses = losses;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ratio ratio = (Ratio) o;
        return id == ratio.id && wins == ratio.wins && losses == ratio.losses && Objects.equals(name, ratio.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wins, losses, name);
    }
}
