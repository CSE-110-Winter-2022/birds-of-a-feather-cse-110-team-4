package com.example.birdsoffeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;


public class searchingActivity extends AppCompatActivity {
    private final String EXURL = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;
    final Context context = this;
    private EditText userSessionInput;

    protected boolean btnStatus = true;

    //TODO: List to store fetch students

    private static final String TAG = "Lab5-Nearby";
    private MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        //initialize local database
        AppDatabase db = AppDatabase.singleton(this);
        List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);

        personRecyclerView = findViewById(R.id.search_recycler_view);

        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);

        // sorting list of students in order of common classes
        Collections.sort(studentList, new Comparator<PersonWithCourses>() {
            @Override
            public int compare(PersonWithCourses p1, PersonWithCourses p2) {
                return p2.getCourses().size() - p1.getCourses().size();
            }
        });

        peopleViewAdapter = new PeopleViewAdapter(studentList);

    }

    //search the nearby student who has taken same classes with the user
    public void searchonClick(View view) {
        Button btn = findViewById(R.id.searchButton);
        //when the btn when status is start
        if(btnStatus) {
            btnStatus = !btnStatus;
            btn.setText("Stop");
            AppDatabase db = AppDatabase.singleton(this);
            List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
            studentList.remove(0);
            peopleViewAdapter = new PeopleViewAdapter(studentList);
            personRecyclerView.setAdapter(peopleViewAdapter);
        }

        //when the btn when status is start
        else {
            btnStatus = !btnStatus;
            btn.setText("Start");
            //pop up window & save session

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.addsession_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.dialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Save",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
//                                    userSessionInput.setText(userInput.getText());

                                    //for the test
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }

    }


    //Go Back
    public void backonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    //Go to the Mock
    public void goToMockOnClick(View view) {
        Intent intent = new Intent(this, MockCSVActivity.class);
        startActivity(intent);
    }

}