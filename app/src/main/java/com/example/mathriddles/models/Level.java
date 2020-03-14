package com.example.mathriddles.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "levels")
public class Level{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "image_id")
    private int questionImageId;

    @NonNull
    @ColumnInfo(name = "help")
    private String help;

    @NonNull
    @ColumnInfo(name = "answer")
    private String answer;

    @NonNull
    @ColumnInfo(name = "is_completed", defaultValue = "false")
    private boolean isCompleted;

    public Level() {
    }

    @Ignore
    public Level(int questionImageId, @NonNull String help, @NonNull String answer) {
        this.questionImageId = questionImageId;
        this.help = help;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionImageId() {
        return questionImageId;
    }

    public void setQuestionImageId(int questionImageId) {
        this.questionImageId = questionImageId;
    }

    @NonNull
    public String getHelp() {
        return help;
    }

    public void setHelp(@NonNull String help) {
        this.help = help;
    }

    @NonNull
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(@NonNull String answer) {
        this.answer = answer;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
