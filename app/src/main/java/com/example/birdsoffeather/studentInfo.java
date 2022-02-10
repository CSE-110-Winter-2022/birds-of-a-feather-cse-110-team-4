package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;

import java.util.List;

public class studentInfo extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person;
    private int personId = 0;
    private  String name;
    private List course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        course = person.getCourses();
        TextView nameView = findViewById(R.id.studentName);
        nameView.setText(name);//Set name for user
        TextView courseView = findViewById(R.id.studentName);
        courseView.setText(course.toString());




    }

    public void backToList(View view) {
        finish();
    }
}