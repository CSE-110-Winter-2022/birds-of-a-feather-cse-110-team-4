package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    protected PeopleViewAdapter peopleViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        db = AppDatabase.singleton(this);
        List<Courses> courses = db.coursesDao().gerForPerson(0);
        List<Courses> currentCourses = new ArrayList<>();
        for(Courses course: courses){
            String split[] = course.course.split(" ");
            if((split[0].equals(currentYear) && split[1].equals(currentQuarter))){
               currentCourses.add(course);
            }
        }

        List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);
        for(int i = studentList.size() - 1; i >= 0; i--){
            if(!favorite(studentList.get(i))){
                studentList.remove(studentList.get(i));
            }
        }
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

    private boolean favorite(IPerson person){
        return person.getFavStatus();
    }
}

