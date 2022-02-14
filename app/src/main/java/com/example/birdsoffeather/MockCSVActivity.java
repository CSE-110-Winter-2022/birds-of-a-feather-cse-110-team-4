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
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.List;

public class MockCSVActivity extends AppCompatActivity {

    private static String found;
    private MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_csvactivity);
    }


    public void onBackClicked(View view) {
        Intent intent = new Intent(this, searchingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Nearby.getMessagesClient(this).subscribe(messageListener);   // something crashes here

    }

    //When clicked button, get student information from csv, and add that student to db
    public void onAddStudentClicked(View view) {
        String name, url;
        List<String> courses = new ArrayList<String>();
        List<String> matches = new ArrayList<String>();

        //get csv input
        TextView infoView = findViewById(R.id.studentInfo);
        String info = infoView.getText().toString();

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                MockCSVActivity.found = new String(message.getContent());
            }

            @Override
            public void onLost(@NonNull Message message) {
            }

        };
        if(!TextUtils.isEmpty(info)) {

            this.messageListener = new FakedMessageListener(realListener, info);
            Nearby.getMessagesClient(this).subscribe(messageListener);

            //format csv input and retrieve relevant information
            if(!TextUtils.isEmpty(found)) {

                List<String> student = CSVReader.ReadCSV(found);
                name = student.get(0);
                url = student.get(1);
                for (int i = 2; i < student.size(); i++) {
                    String newCourse = student.get(i);
                    courses.add(newCourse);
                }
                found = "";
                //get user's courses for comparing
                AppDatabase db = AppDatabase.singleton(this);
                List<Courses> myCourses = db.coursesDao().gerForPerson(0);
                ArrayList<String> myCoursesList = new ArrayList<String>();
                for (Courses c : myCourses) {
                    myCoursesList.add(c.course);
                }
                compareCourses(courses, myCoursesList, matches);
                infoView.setText("");

                if (matches.size() > 0) {
                    //add person to db
                    int nextID = db.personsWithCoursesDao().getAll().size();
                    Person newStudent = new Person(nextID, name, url);
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
        } else {
            Toast.makeText(this, "No Empty Entries!",Toast.LENGTH_SHORT).show();
        }


    }

    //Compare my course and the entered student's course, store matching course in matches
    private void compareCourses(List<String> c1, List<String> c2, List<String> matches) {
        for(String str: c2) {
            if(c1.contains(str)) {
                matches.add(str);
            }
        }
    }

    public void onGoToBoFClicked(View view) {
    }
}