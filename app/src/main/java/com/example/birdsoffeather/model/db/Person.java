package com.example.birdsoffeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "persons")
public class Person {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int personId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "headshot")
    public String imageURL;

    public Person(int id, String name, String url) {
        this.personId = id;
        this.name = name;
        this.imageURL = url;
    }

    public Person() {
        super();
    }
}
