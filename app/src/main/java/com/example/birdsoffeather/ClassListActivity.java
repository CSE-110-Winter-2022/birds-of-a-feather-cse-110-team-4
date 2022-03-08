package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.PersonWithCourses;

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
        for(Courses course: courses){
            if(!thisQuarter(course)){
               courses.remove(course);
            }
        }
        List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);
        for(PersonWithCourses student: studentList){
            if(!favorite(student)){
                studentList.remove(student);
            }
        }
        RecyclerView Classes = findViewById(R.id.ClassesList);
        personRecyclerView = findViewById(R.id.FavoriteList);
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
        adapter = new ClassViewAdapter(false, courses, (course)-> {
            db.coursesDao().delete(course);
        });
        Classes.setAdapter(adapter);
        Classes.setLayoutManager(new LinearLayoutManager(this));
        personRecyclerView.setAdapter(peopleViewAdapter);
        personRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private boolean thisQuarter(Courses course) {
        String[] courseString = course.course.split(" ");
        String quarter = courseString[1];
        String year = courseString[0];
        if(quarter.equals(currentQuarter) && year.equals(currentYear)){
            return true;
        }
        return false;
    }

    private boolean favorite(IPerson person){
        return person.getFavStatus();
    }
}

