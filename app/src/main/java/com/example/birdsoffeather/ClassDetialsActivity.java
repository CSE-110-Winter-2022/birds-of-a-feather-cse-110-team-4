package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.widget.TextView;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.List;

public class ClassDetialsActivity extends AppCompatActivity {
    private AppDatabase db;
    private List<PersonWithCourses> studentList;
    private  String year;
    private String quarter;
    private String course;
    private String subject;
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detials);
        db = AppDatabase.singleton(this);
        studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        quarter = intent.getStringExtra("quarter");
        course = intent.getStringExtra("course");
        subject = intent.getStringExtra("subject");
        List<PersonWithCourses> classmates = new ArrayList<PersonWithCourses>();
        System.out.println(studentList.size());
        for(int i = studentList.size() - 1; i >= 0; i--){
            List<Courses> courses = db.coursesDao().gerForPerson(studentList.get(i).getId());
            for(Courses course: courses){
                System.out.println(course.course);
                if(isSameCourse(course)){
                    classmates.add(studentList.get(i));
                }
            }
        }

        TextView courseName = findViewById(R.id.Course);
        courseName.setText(year + " " + quarter + " " + subject + " " + course);
        personRecyclerView = findViewById(R.id.ClassmatesList);
        peopleViewAdapter = new PeopleViewAdapter(classmates, this, db);
        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);
        personRecyclerView.setAdapter(peopleViewAdapter);

    }
    private boolean isSameCourse(Courses course){
        String[] splitCousrse = course.course.split(" ");
        if(splitCousrse[0].equals(this.year) && splitCousrse[1].equals(this.quarter) && splitCousrse[2].equals(this.subject) && splitCousrse[3].equals(this.course)){
            return true;
        }
        return false;
    }
}