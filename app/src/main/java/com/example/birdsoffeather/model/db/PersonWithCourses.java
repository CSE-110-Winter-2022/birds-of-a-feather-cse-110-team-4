package com.example.birdsoffeather.model.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import com.example.birdsoffeather.model.IPerson;

public class PersonWithCourses implements IPerson{
    @Embedded
    public Person person;

    @Relation(parentColumn = "id",
            entityColumn = "person_id",
            entity = Courses.class,
            projection = {"course"})
    public List<String> Courses;

    @Override
    public int getId() {
        return this.person.personId;
    }

    @Override
    public String getName() {
        return this.person.name;
    }

    public String getURL() {
        return  this.person.imageURL;
    }

    @Override
    public  List<String> getCourses(){
        return this.Courses;
    }
}