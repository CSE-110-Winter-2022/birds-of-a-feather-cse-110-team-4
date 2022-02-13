package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class searchingActivity extends AppCompatActivity {
    private final String EXURL = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;

    protected List<PersonWithCourses> testData = new ArrayList<PersonWithCourses>(Arrays.asList(
            new PersonWithCourses(new Person(100, "Arif", EXURL), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110", "CSE 123"))),
            new PersonWithCourses(new Person(101, "Stephan", EXURL), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110"))),
            new PersonWithCourses(new Person(102, "Charles", EXURL), new ArrayList<String>(Arrays.asList("CSE 111", "CSE 110")))
    ));

    //TODO: List to store fetch students

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        personRecyclerView = findViewById(R.id.search_recycler_view);

        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);

        // sorting list of students in order of common classes
        Collections.sort(testData, new Comparator<PersonWithCourses>() {
            @Override
            public int compare(PersonWithCourses p1, PersonWithCourses p2) {
                return p2.getCourses().size() - p1.getCourses().size();
            }
        });

        peopleViewAdapter = new PeopleViewAdapter(testData);


    }


    public void searchonClick(View view) {
        personRecyclerView.setAdapter(peopleViewAdapter);

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