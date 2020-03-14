package com.example.mathriddles.datasource;

import com.example.mathriddles.dao.LevelDAO;
import com.example.mathriddles.models.Level;

import java.util.List;

import io.reactivex.Flowable;

public class LevelDatasource implements ILevelDatasource {

    private LevelDAO dao;

    private static LevelDatasource INSTANCE;

    public LevelDatasource(LevelDAO dao) {
        this.dao = dao;
    }

    public static LevelDatasource getInstance(LevelDAO dao) {
        if (INSTANCE == null) {
            INSTANCE = new LevelDatasource(dao);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<Level> getLevelById(int levelId) {
        return dao.getLevelById(levelId);
    }

    @Override
    public Flowable<List<Level>> getAll() {
        return dao.getAll();
    }

    @Override
    public void insertLevel(Level... levels) {
        dao.insertLevel(levels);
    }

    @Override
    public void updateLevel(Level... levels) {
        dao.updateLevel(levels);
    }

    @Override
    public void deleteLevel(Level... levels) {
        dao.deleteLevel(levels);
    }

    @Override
    public void deleteFromLevel() {
        dao.deleteFromLevel();
    }
}
