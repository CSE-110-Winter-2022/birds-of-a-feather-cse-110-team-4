package com.example.birdsoffeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//data structure for Person in database
@Entity(tableName = "persons")
public class Person {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int personId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "headshot")
    public String imageURL;

    public Person(int personId, String name, String imageURL){
        this.personId = personId;
        this.name = name;
        this.imageURL = imageURL;
    }
}
