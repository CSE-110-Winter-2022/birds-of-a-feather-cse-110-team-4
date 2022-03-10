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
public interface PersonWithCoursesDao {
    @Transaction
    @Query("SELECT * FROM persons")
    List<PersonWithCourses> getAll();

    @Query("SELECT * FROM persons WHERE id = :id")
    PersonWithCourses get(String id);

    @Query("SELECT COUNT(*) from persons")
    int count();

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);
}
