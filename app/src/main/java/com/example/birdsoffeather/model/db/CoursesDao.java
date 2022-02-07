package com.example.birdsoffeather.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;

@Dao
public interface CoursesDao {
    @Transaction
    @Query("SELECT * FROM classes where person_id =:personId")
    List<Courses> gerForPerson(int personId);

    @Query("SELECT * FROM classes WHERE id = :id")
    Courses get(int id);

    @Query("SELECT COUNT(*) from classes")
    int count();

    @Insert
    void insert(Courses courses);

    @Delete
    void delete(Courses courses);
}
