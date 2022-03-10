package com.example.birdsoffeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "classes")

//data structure for courses in database
public class Courses {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int classId;

    @ColumnInfo(name = "person_id")
    public String personId;

    @ColumnInfo(name = "Tag")
    public String Tag;

    @ColumnInfo(name = "course")
    public String course;


    public Courses(int classId, String personId, String course){
        this.classId = classId;
        this.personId = personId;
        this.course = course;
        this.Tag = course;
    }

    public int getId(){
        return this.classId;
    }
}
