package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Arrays;

public class searchingActivity extends AppCompatActivity {
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdaper;

    protected IPerson[] testData = {
            new PersonWithCourses(new Person(101, "Charles", "123"), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110"))),
            new PersonWithCourses(new Person(102, "Stephan", "123"), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110"))),
    };
    //TODO: List to store fetch students

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
    }

    public void searchonClick(View view) {
    }

    public void backonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void goToMockOnClick(View view) {
        Intent intent = new Intent(this, MockCSVActivity.class);
        startActivity(intent);
    }
}