package com.example.birdsoffeather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")

//data structure for courses in database
public class UserId {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int UserId;

    @ColumnInfo(name = "UUID")
    public String UUID;



    public UserId(int UserId, String UUID){
        this.UserId = UserId;
        this.UUID = UUID;
    }

    public String getUUID(){
        return this.UUID;
    }
}