package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;
import com.example.birdsoffeather.model.db.UserId;

import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person1;
    String userID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize database and user's information at login page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = AppDatabase.singleton(this);
        List<PersonWithCourses> persons = db.personsWithCoursesDao().getAll();

        //Insert an user into database if the user log in our app first time
        if(persons.size() == 0) {
            userID = UUID.randomUUID().toString();
            Person newPerson1 = new Person(userID, "Daniel Luther", "", false, false, false);
            db.personsWithCoursesDao().insert(newPerson1);
            UserId userId = new UserId(0,userID);
            db.userIdDao().insert(userId);

        }
        else{
            userID = db.userIdDao().get(0).getUUID();
        }
    }

    //Go to profile class
    public void LoginButtonClick(View view) {
        Intent intent = new Intent(this, profileActivity.class);
        intent.putExtra("user_id", userID);
        startActivity(intent);
    }
}