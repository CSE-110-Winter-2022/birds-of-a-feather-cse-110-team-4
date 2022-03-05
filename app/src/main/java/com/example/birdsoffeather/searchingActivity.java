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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.birdsoffeather.model.CSVReader;
import com.example.birdsoffeather.model.MsgListener;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;
import com.example.birdsoffeather.model.db.Person;
import com.example.birdsoffeather.model.db.PersonWithCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private static final String TAG = "Lab5-Nearby";
    private MessageListener messageListener;
    private List<PersonWithCourses> studentList;
    private String myInfoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: create database
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        setTitle("Searching");

        //initialize local database
        AppDatabase db = AppDatabase.singleton(this);
        studentList = db.personsWithCoursesDao().getAll();
        studentList.remove(0);

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
        Collections.sort(studentList, new Comparator<PersonWithCourses>() {
            @Override
            public int compare(PersonWithCourses p1, PersonWithCourses p2) {
                return p2.getCourses().size() - p1.getCourses().size();
            }
        });

        for(PersonWithCourses p : studentList) {
            System.out.println(p.getName());
        }
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
        myInfoStr = createMyInfoStr();
    }

    //Creates the string of current user's info, to be sent to other users via nearby msg
    private String createMyInfoStr() {
        AppDatabase db = AppDatabase.singleton(this);
        //get my courses
        List<String> myCourses = db.personsWithCoursesDao().get(0).getCourses();
        //get my name and url
        String str = db.personsWithCoursesDao().get(0).getName();
        str += ",,,\n";
        str += db.personsWithCoursesDao().get(0).getURL() + ",,,\n";
        //add all courses into string
        for(int i = 0; i < myCourses.size(); i++) {
            String[] split = myCourses.get(i).split(" ");
            str += split[0] + ","+ split[1] + "," + split[2] + ","+ split[3] + ","+ split[4];
            if(i != myCourses.size() - 1)
                str += "\n";
        }
        return str;
    }

    public float recentPriorityScore(PersonWithCourses person) {
        float score = 0;
        for (int i =0; i < person.getCourses().size(); i++) {
            String course = person.getCourses().get(i);
            String[] courseDetails = courseDetails(course);

            // Grab Relevant Class Details
            int year = Integer.parseInt(courseDetails[0]);
            int quarterOffset = 0;
            if (courseDetails[1].equals("FA")) {
                quarterOffset = -3;
            } else if (courseDetails[1].equals("SS1") || courseDetails[1].equals("SS2") || courseDetails[1].equals("SSS")) {
                quarterOffset = -2;
            } else if (courseDetails[1].equals("SP")) {
                quarterOffset = -1;
            } else if (courseDetails[1].equals("WI")) {
                quarterOffset = 0;
            }

            // Assuming this is for WI 2022
            int yearDif = 2022 - year;
            int age = yearDif * 4 + quarterOffset;

            if (age == 0) {
                score = 5;
            } else if (age == 1) {
                score = 4;
            } else if (age == 2) {
                score = 3;
            } else if (age == 3) {
                score = 2;
            } else if (age >= 4) {
                score = 1;
            }
        }
        return score;
    }

    public float sizePriorityScore(PersonWithCourses person) {
        float score = 0;
        for (int i =0; i < person.getCourses().size(); i++) {
            String course = person.getCourses().get(i);
            String[] courseDetails = courseDetails(course);

            // Grab Relevant Class Details
            String size = courseDetails[4];

            // Calculate weight
            if (size.equals("Tiny")) {
                score += 1;
            } else if (size.equals("Small")) {
                score += .33;
            } else if (size.equals("Medium")) {
                score += .18;
            } else if (size.equals("Large")) {
                score += .1;
            } else if (size.equals("Huge")) {
                size += .06;
            } else if (size.equals("Gigantic")) {
                score += .03;
            }
        }

        return score;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        System.out.println("clicked");
        if(parent.getId() == R.id.sortingSpinner) {
            Collections.sort(studentList, new Comparator<PersonWithCourses>() {
                @Override
                public int compare(PersonWithCourses p1, PersonWithCourses p2) {
                    String option = (String) parent.getItemAtPosition(pos);
                    if (option.equals("Prioritize Recent")) {
                        return sizePriorityScore(p1) > sizePriorityScore(p2) ? -1 : 1;
                    } else if (option.equals("Prioritize Small Classes")) {
                        return recentPriorityScore(p1) > recentPriorityScore(p2) ? -1 : 1;
                    }
                    return p2.getCourses().size() - p1.getCourses().size();
                }
            });
        }
        AppDatabase db = AppDatabase.singleton(this);
        for(PersonWithCourses p : studentList) {
            System.out.println(p.getName());
        }
        peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
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
            AppDatabase db = AppDatabase.singleton(this);
            List<PersonWithCourses> studentList = db.personsWithCoursesDao().getAll();
            studentList.remove(0);
            peopleViewAdapter = new PeopleViewAdapter(studentList, this, db);
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
                        if(lines[1].equals(db.personsWithCoursesDao().get(0).getName())) {
                            for(PersonWithCourses p : db.personsWithCoursesDao().getAll()) {
                                if(p.getName().equals(lines[2])) {
                                    //update that this person waved to me
                                    Person newPerson = new Person(db.personsWithCoursesDao().getAll().size(),
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
                        if(name.equals(db.personsWithCoursesDao().get(0).getName())) {
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
                        List<Courses> myCourses = db.coursesDao().gerForPerson(0);
                        ArrayList<String> myCoursesList = new ArrayList<String>();
                        for (Courses c : myCourses) {
                            myCoursesList.add(c.course);
                        }
                        List<String> matches = new ArrayList<>();
                        compareCourses(courses, myCoursesList, matches);
                        if (matches.size() > 0) {
                            //add person to db
                            int nextID = db.personsWithCoursesDao().getAll().size();
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
                }

            };
            this.messageListener = new MsgListener(realListener, 5, myInfoStr);
            Nearby.getMessagesClient(this).subscribe(messageListener);
        }
        //when the btn when status is start
        else {
            btnStatus = !btnStatus;
            btn.setText("Start");
            //unsubscribe msg listener
            Nearby.getMessagesClient(this).unsubscribe(messageListener);
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