package com.example.mathriddles.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mathriddles.models.Level;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LevelDAO {

    @Query("SELECT * FROM levels WHERE id=:levelId")
    Flowable<Level> getLevelById(int levelId);

    @Query("SELECT * FROM Levels")
    Flowable<List<Level>> getAll();

    @Insert
    void insertLevel(Level... levels);


    @Update
    void updateLevel(Level... levels);

    @Delete
    void deleteLevel(Level... levels);

    @Query("DELETE FROM levels")
    void deleteFromLevel();
}
