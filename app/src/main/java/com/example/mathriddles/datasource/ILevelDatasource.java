package com.example.mathriddles.datasource;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mathriddles.models.Level;

import java.util.List;

import io.reactivex.Flowable;

public interface ILevelDatasource {

    Flowable<Level> getLevelById(int levelId);

    Flowable<List<Level>> getAll();

    void insertLevel(Level... levels);

    void updateLevel(Level... levels);

    void deleteLevel(Level... levels);

    void deleteFromLevel();
}
