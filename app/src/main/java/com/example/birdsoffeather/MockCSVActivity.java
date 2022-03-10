package com.example.birdsoffeather;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdsoffeather.model.CSVReader;
import com.example.birdsoffeather.model.FakedMessageListener;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.List;

public class MockCSVActivity extends AppCompatActivity {

    private static String found;
    private MessageListener messageListener;
    private boolean searching;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_csvactivity);
        Intent intent = getIntent();
        searching = intent.getBooleanExtra("Searching",true);
        uuid = intent.getStringExtra("uuid");
    }

    //Go back to search BOF page
    public void onBackClicked(View view) {
        Intent intent = new Intent(this, searchingActivity.class);
        startActivity(intent);
    }

    //When clicked button, get student information from csv, and add that student to db
    public void onAddStudentClicked(View view) {
        if(!searching) {
            Toast.makeText(this, "Cannot mock when not searching!",Toast.LENGTH_SHORT).show();
            return;
        }

        //get csv input
        TextView infoView = findViewById(R.id.studentInfo);
        String info = infoView.getText().toString();

        if(!TextUtils.isEmpty(info)) {

            //format csv input and retrieve relevant information

            List<String> student = CSVReader.ReadCSV(info);
            if(!student.get(student.size() - 1).equals("wave")) {
                addStudent(student);
            }else {
                mockWave(student);
            }
        } else {
            Toast.makeText(this, "No Empty Entries!",Toast.LENGTH_SHORT).show();
        }


    }

    private void addStudent(List<String> student) {
        String name, url;
        List<String> courses = new ArrayList<String>();
        List<String> matches = new ArrayList<String>();
        TextView infoView = findViewById(R.id.studentInfo);
        String info = infoView.getText().toString();
        String othersUUID = student.get(0);
        //TODO: uuid
        name = student.get(1);
        url = student.get(2);
        for (int i = 3; i < student.size(); i++) {
            String newCourse = student.get(i);
            courses.add(newCourse);
            System.out.println(newCourse);
        }
        info = "";
        //get user's courses for comparing
        AppDatabase db = AppDatabase.singleton(this);
        List<Courses> myCourses = db.coursesDao().gerForPerson(uuid);
        ArrayList<String> myCoursesList = new ArrayList<String>();
        for (Courses c : myCourses) {
            myCoursesList.add(c.course);
            System.out.println("my course: "+c.course);
        }
        compareCourses(courses, myCoursesList, matches);
        infoView.setText("");

        if (matches.size() > 0) {
            //add person to db
            Person newStudent = new Person(othersUUID, name, url, false, false, false);
            db.personsWithCoursesDao().insert(newStudent);

            //add matching classes to db
            int newCourseId = db.coursesDao().count() + 1;
            for (String course : matches) {
                Courses newCourse = new Courses(newCourseId, newStudent.personId, course);
                db.coursesDao().insert(newCourse);
                newCourseId++;
            }
        } else {
            Toast.makeText(this, "No Matching Courses!", Toast.LENGTH_SHORT).show();
        }
    }

    private void mockWave(List<String> student) {
        TextView infoView = findViewById(R.id.studentInfo);
        found = "";
        infoView.setText("");
        String uuid = student.get(0);
        AppDatabase db = AppDatabase.singleton(this);
        //TODO: implement myid
        String myid = uuid;
        if(student.get(student.size()-2).equals("4b295157-ba31-4f9f-8401-5d85d9cf659a")) {
            PersonWithCourses person = db.personsWithCoursesDao().get(uuid);
            //Person p = db.personsWithCoursesDao().get(student.get(0));
            Person newPerson = new Person(person.getId(), person.getName(), person.getURL(), person.getWaveTo(), true, person.getFavStatus());
            db.personsWithCoursesDao().update(newPerson);
        }
    }

    //Compare my course and the entered student's course, store matching course in matches
    private void compareCourses(List<String> c1, List<String> c2, List<String> matches) {
        for(String str: c2) {
            System.out.println(str);
            System.out.println(c1.get(0));
            if(c1.contains(str)) {
                matches.add(str);
            }
        }
    }

    public void onGoToBoFClicked(View view) {
    }
}