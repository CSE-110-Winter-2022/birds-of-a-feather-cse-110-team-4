package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {
    private AppDatabase db;
    private ClassViewAdapter adapter;
    protected RecyclerView personRecyclerView;
    private String currentQuarter = "WI";
    private String currentYear = "2022";
    private String personId;
    protected PeopleViewAdapter peopleViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Intent intent = getIntent();
        personId = intent.getStringExtra("user_id");
        db = AppDatabase.singleton(this);
        List<Courses> courses = db.coursesDao().gerForPerson(personId);
        List<Courses> currentCourses = new ArrayList<>();

        //Add only the course on current quarter
        for(Courses course: courses){
            if(isCurrentQuarter(course)){
               currentCourses.add(course);
            }
        }

        //Add only favorite person
        List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
        for(PersonWithCourses person: studentList){
            if(person.getId().equals(personId)){
                studentList.remove(person);
            }
        }
        for(int i = studentList.size() - 1; i >= 0; i--){
            if(!favorite(studentList.get(i))){
                studentList.remove(studentList.get(i));
            }
        }

        //Initialize the recyclerView
        RecyclerView Classes = findViewById(R.id.ClassesList);
        personRecyclerView = findViewById(R.id.FavoriteList);
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
        adapter = new ClassViewAdapter(false,false, currentCourses, (course)-> {
            db.coursesDao().delete(course);
        });
        Classes.setAdapter(adapter);
        Classes.setLayoutManager(new LinearLayoutManager(this));
        personRecyclerView.setAdapter(peopleViewAdapter);
        personRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //helper function for checking if the course is in current quarter
    private boolean isCurrentQuarter(Courses course){
        String split[] = course.course.split(" ");
        if((split[0].equals(currentYear) && split[1].equals(currentQuarter))){
            return true;
        }
        return false;
    }

    //helper function for checking if the person is favorite
    private boolean favorite(IPerson person){
        return person.getFavStatus();
    }
}

