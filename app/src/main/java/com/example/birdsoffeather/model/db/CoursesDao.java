package com.example.birdsoffeather.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

//data interaction for Courses
@Dao
public interface CoursesDao {
    @Transaction
    @Query("SELECT * FROM classes where person_id =:personId")
    List<Courses> gerForPerson(String personId);

    @Query("SELECT * FROM classes WHERE id = :id")
    Courses get(int id);

    @Query("SELECT COUNT(*) from classes")
    int count();

    @Insert
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);
}
