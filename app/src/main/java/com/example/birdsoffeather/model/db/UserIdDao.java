package com.example.birdsoffeather.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;


//PerssonWithCourses interaction
@Dao
public interface UserIdDao {
    @Transaction

    @Query("SELECT * FROM users WHERE id = :id")
    UserId get(int id);

    @Insert
    void insert(UserId UserId);

    @Update
    void update(UserId UserId);
}

