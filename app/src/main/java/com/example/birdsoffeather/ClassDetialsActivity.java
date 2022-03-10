package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.view.View;
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
    private String userID;
    private  Courses thisCourse;
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;
    private TextView courseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detials);
        db = AppDatabase.singleton(this);

        studentList = db.personsWithCoursesDao().getAll();
        for (PersonWithCourses person: studentList){
            if(person.getName().equals("Daniel Luther")){
                userID = person.getId();
            }
        }
        for(PersonWithCourses person: studentList){
            if(person.getId().equals(userID)){
                studentList.remove(person);
            }
        }
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        quarter = intent.getStringExtra("quarter");
        course = intent.getStringExtra("course");
        subject = intent.getStringExtra("subject");
        List<PersonWithCourses> classmates = new ArrayList<PersonWithCourses>();
        List<Courses> myCourses = db.coursesDao().gerForPerson(userID);
        for(Courses course: myCourses){
            if(isSameCourse(course)){
                thisCourse = course;
            }
        }
        //check if the student have taken the same classes with us
        for(int i = studentList.size() - 1; i >= 0; i--){
            List<Courses> courses = db.coursesDao().gerForPerson(studentList.get(i).getId());
            for(Courses course: courses){
                System.out.println(course.course);
                if(isSameCourse(course)){
                    classmates.add(studentList.get(i));
                }
            }
        }

        //Initialize the recycler view
        courseName = findViewById(R.id.CourseName);
        courseName.setText(thisCourse.Tag);
        personRecyclerView = findViewById(R.id.ClassmatesList);
        peopleViewAdapter = new PeopleViewAdapter(classmates, this, db);
        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);
        personRecyclerView.setAdapter(peopleViewAdapter);

    }

    //helper function for checking if the student take the same class with us
    private boolean isSameCourse(Courses course){
        String[] splitCousrse = course.course.split(" ");
        if(splitCousrse[0].equals(this.year) && splitCousrse[1].equals(this.quarter) && splitCousrse[2].equals(this.subject) && splitCousrse[3].equals(this.course)){
            return true;
        }
        return false;
    }

    public void backonClick(View view) {
        String text = courseName.getText().toString();
        thisCourse.Tag = text;
        db.coursesDao().update(thisCourse);
        finish();
    }
}