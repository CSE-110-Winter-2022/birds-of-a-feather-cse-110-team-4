package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person1;
    private IPerson person2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize database and user's information at login page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = AppDatabase.singleton(this);
        person1 = db.personsWithCoursesDao().get(0);
        person2 = db.personsWithCoursesDao().get(1);
        if(person1 == null) {
            Person newPerson1 = new Person(0, "Daniel Luther", "");
            db.personsWithCoursesDao().insert(newPerson1);
        }
        if(person2 == null) {
            Person newPerson2 = new Person(1, "John Arther", "");
            db.personsWithCoursesDao().insert(newPerson2);
        }
    }

    public void LoginButtonClick(View view) {
        Intent intent = new Intent(this, profileActivity.class);
        startActivity(intent);
    }

    public void testOnclick(View view) {
        Intent intent = new Intent(this, studentInfo.class);
        intent.putExtra("Person_id", 0);
        startActivity(intent);
    }
}