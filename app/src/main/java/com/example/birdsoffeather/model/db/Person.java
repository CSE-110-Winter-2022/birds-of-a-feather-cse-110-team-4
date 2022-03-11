package com.example.birdsoffeather.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//data structure for Person in database
@Entity(tableName = "persons")
public class Person {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public String personId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "headshot")
    public String imageURL;

    @ColumnInfo(name = "userID")
    public String userID;

    @ColumnInfo(name = "waveto")
    public boolean waveTo;

    @ColumnInfo(name = "wavefrom")
    public boolean waveFrom;

    @ColumnInfo(name = "favorite")
    public boolean favorite;

    public Person(String personId, String name, String imageURL, boolean waveTo, boolean waveFrom, boolean favorite){
        this.personId = personId;
        this.name = name;
        this.imageURL = imageURL;
        this.waveTo =  waveTo;
        this.waveFrom = waveFrom;
        this.favorite = favorite;
        this.userID = "";
    }
}
