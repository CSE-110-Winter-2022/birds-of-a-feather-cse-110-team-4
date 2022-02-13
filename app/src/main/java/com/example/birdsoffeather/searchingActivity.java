package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchingActivity extends AppCompatActivity {
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;
    private AppDatabase db;
    protected IPerson[] testData = {
            new PersonWithCourses(new Person(101, "Charles", "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0"), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110"))),
            new PersonWithCourses(new Person(102, "Stephan", "123"), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110"))),
    };

    //TODO: List to store fetch students
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        RecyclerView searchRecyclerView = findViewById(R.id.search_recycler_view);
        personLayoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(personLayoutManager);
        peopleViewAdapter = new PeopleViewAdapter(testData);
        searchRecyclerView.setAdapter(peopleViewAdapter);

    }

    /*public void searchonClick(View view) {
        RecyclerView searchRecyclerView = findViewById(R.id.search_recycler_view);
        peopleViewAdapter = new PeopleViewAdapter(testData);
        searchRecyclerView.setAdapter(peopleViewAdapter);
    }*/

    public void backonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void goToMockOnClick(View view) {
        Intent intent = new Intent(this, MockCSVActivity.class);
        startActivity(intent);
    }
}