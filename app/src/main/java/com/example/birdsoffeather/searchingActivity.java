package com.example.birdsoffeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.birdsoffeather.model.CSVReader;
import com.example.birdsoffeather.model.MsgListener;
import com.example.birdsoffeather.model.MultiWayComparator;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;


public class searchingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final String EXURL = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
    protected RecyclerView personRecyclerView;
    protected RecyclerView.LayoutManager personLayoutManager;
    protected PeopleViewAdapter peopleViewAdapter;
    final Context context = this;
    private EditText userSessionInput;

    protected boolean btnStatus = true;

    //TODO: List to store fetch students

    public static final String TAG = "BoF";
    private MessageListener messageListener;
    private List<PersonWithCourses> studentList;
    private String myInfoStr;
    private Message msg;
    private boolean accessibility = false;
    private AppDatabase db;
    private boolean isSearching = false;
    private String myUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        //Initialize accessibility toggle
        ToggleButton toggle = (ToggleButton) findViewById(R.id.visibilityToggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    accessibility = true;
                    Activity host = (Activity) findViewById(R.id.visibilityToggle).getContext();
                    Button b = findViewById(R.id.searchButton);
                    if(host != null && b.getText() == "Stop") {
                        Nearby.getMessagesClient(host).publish(msg);
                        Log.d(TAG, "My info published");
                    }
                } else {
                    accessibility = false;
                }
            }
        });
        //initialize local database
        db = AppDatabase.singleton(this);
        myUUID = db.userIdDao().get(0).getUUID();
        studentList = db.personsWithCoursesDao().getAll();
        for (int i = 0;i < studentList.size();i++){
            if(studentList.get(i).getId().equals(myUUID)){
                studentList.remove(i);
            }
        }
        System.out.println(myUUID);
        personRecyclerView = findViewById(R.id.search_recycler_view);

        personLayoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(personLayoutManager);

        Spinner spinnerView = (Spinner) findViewById(R.id.sortingSpinner);
        ArrayAdapter<CharSequence> sortOptionsAdapter = ArrayAdapter.createFromResource(this,
                R.array.Sorting_Options, android.R.layout.simple_spinner_item);
        sortOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerView.setAdapter(sortOptionsAdapter);
        String spinnerOption = (String) spinnerView.getItemAtPosition(0);
        spinnerView.setOnItemSelectedListener(this);

        // sorting list of students in order of common classes
        Collections.sort(studentList, new MultiWayComparator("Default"));
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
        myInfoStr = createMyInfoStr();
        msg = new Message(myInfoStr.getBytes());
    }

    //Creates the string of current user's info, to be sent to other users via nearby msg
    private String createMyInfoStr() {
        AppDatabase db = AppDatabase.singleton(this);
        //get my courses
        List<String> myCourses = db.personsWithCoursesDao().get(myUUID).getCourses();
        //get my name and url
        String str = db.personsWithCoursesDao().get(myUUID).getName();
        str += ",,,\n";
        str += db.personsWithCoursesDao().get(myUUID).getURL() + ",,,\n";
        //add all courses into string
        for(int i = 0; i < myCourses.size(); i++) {
            String[] split = myCourses.get(i).split(" ");
            str += split[0] + ","+ split[1] + "," + split[2] + ","+ split[3] + ","+ split[4];
            if(i != myCourses.size() - 1)
                str += "\n";
        }
        return str;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        studentList = db.personsWithCoursesDao().getAll();
        for (int i = 0;i < studentList.size();i++){
            if(studentList.get(i).getId().equals(myUUID)){
                studentList.remove(i);
            }
        }
        if(parent.getId() == R.id.sortingSpinner) {
            String option = (String) parent.getItemAtPosition(pos);
            Collections.sort(studentList, new MultiWayComparator(option));
        }
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
        personRecyclerView.setAdapter(peopleViewAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public String[] courseDetails(String course) {
        String[] splitStr = course.split(" ");
        return splitStr;
    }



    //search the nearby student who has taken same classes with the user
    public void searchonClick(View view) {
        Button btn = findViewById(R.id.searchButton);
        //when the btn when status is start
        if(btnStatus) {
            btnStatus = !btnStatus;
            btn.setText("Stop");
            isSearching = !isSearching;
            personRecyclerView.setAdapter(peopleViewAdapter);

            //Continously send myinfoStr
            MessageListener realListener = new MessageListener() {
                //Onfound msg: check if msg is wave or not, if wave, store wave, if not, store student
                @Override
                public void onFound(@NonNull Message message) {
                    String found = new String(message.getContent());
                    Log.d(TAG, "found message: " + found);
                    String[] lines = found.split("\n");
                    if(lines[0].equals("wave")) {
                        //check if wave for me
                        if(lines[1].equals(db.personsWithCoursesDao().get(myUUID).getName())) {
                            for(PersonWithCourses p : db.personsWithCoursesDao().getAll()) {
                                if(p.getName().equals(lines[2])) {
                                    //update that this person waved to me
                                    Person newPerson = new Person(UUID.randomUUID().toString(),
                                            p.getName(), p.getURL(), p.getWaveTo(), true, p.getFavStatus());
                                    db.personsWithCoursesDao().update(newPerson);
                                }
                            }
                        }
                    }
                    //add student into db
                    else {
                        List<String> student = CSVReader.ReadCSV(found);
                        String name = student.get(0);
                        //check if it's my info
                        if(name.equals(db.personsWithCoursesDao().get(myUUID).getName())) {
                            return;
                        }
                        //check if it already existed
                        for(PersonWithCourses p: db.personsWithCoursesDao().getAll()) {
                            if(p.getName().equals(name)) {
                                return;
                            }
                        }
                        String url = student.get(1);
                        List<String> courses = new ArrayList<>();
                        for (int i = 2; i < student.size(); i++) {
                            String newCourse = student.get(i);
                            courses.add(newCourse);
                        }
                        //get user's courses for comparing
                        List<Courses> myCourses = db.coursesDao().gerForPerson(myUUID);
                        ArrayList<String> myCoursesList = new ArrayList<String>();
                        for (Courses c : myCourses) {
                            myCoursesList.add(c.course);
                        }
                        List<String> matches = new ArrayList<>();
                        compareCourses(courses, myCoursesList, matches);
                        if (matches.size() > 0) {
                            //add person to db
                            String nextID = UUID.randomUUID().toString();;
                            Person newStudent = new Person(nextID, name, url, false, false, false);
                            db.personsWithCoursesDao().insert(newStudent);

                            //add matching classes to db
                            int newCourseId = db.coursesDao().count() + 1;
                            for (String course : matches) {
                                Courses newCourse = new Courses(newCourseId, newStudent.personId, course);
                                db.coursesDao().insert(newCourse);
                                newCourseId++;
                            }
                        }
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

                @Override
                public void onLost(@NonNull Message message) {
                    Log.d(TAG, "lost message");
                }

            };
            this.messageListener = realListener;
            //this.messageListener = new MsgListener(realListener, 5, myInfoStr);
            if(accessibility) {
                Nearby.getMessagesClient(this).publish(msg);
                Log.d(TAG, "My info published" + myInfoStr);
            }
            Nearby.getMessagesClient(this).subscribe(messageListener);
            Log.d(TAG, "Message listener subscribed");
        }
        //when the btn when status is start
        else {
            btnStatus = !btnStatus;
            btn.setText("Start");
            //unsubscribe msg listener
            Nearby.getMessagesClient(this).unpublish(msg);
            Log.d(TAG, "My info unpublished" + myInfoStr);
            Nearby.getMessagesClient(this).unsubscribe(messageListener);
            Log.d(TAG, "Message listener unsubscribed");
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
        intent.putExtra("Searching",isSearching);
        intent.putExtra("uuid",myUUID);
        startActivity(intent);
    }

}