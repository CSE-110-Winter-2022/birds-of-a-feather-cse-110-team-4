package com.example.birdsoffeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class Courses {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int classId;

    @ColumnInfo(name = "person_id")
    public int personId;

    @ColumnInfo(name = "course")
    public String course;

    public Courses(int classId, int personId, String course){
        this.classId = classId;
        this.personId = personId;
        this.course = course;
    }
}
