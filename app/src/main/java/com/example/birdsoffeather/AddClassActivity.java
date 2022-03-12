package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String selectedYear, selectedQuarter,selectedSize, subject, course;
    //private List<Class> emptyClasses = new ArrayList<Class>();
    //private List<Class> enteredClasses = new ArrayList<Class>();
    private ClassViewAdapter adapter;
    private AppDatabase db;
    private IPerson person;
    private String personId;
    public Class toClass(String data){
        String[] splitStr = data.split(" ");
        return new Class(splitStr[0],splitStr[1],splitStr[2],splitStr[3], splitStr[4]);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        Intent intent = getIntent();
        personId = intent.getStringExtra( "person_id");
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        List<Courses> courses = db.coursesDao().gerForPerson(personId);

        //Set up Spinner(Dropdowns)
        Spinner yearSpinner = (Spinner) findViewById(R.id.YearDropDown);
        Spinner quarterSpinner = (Spinner) findViewById(R.id.QuarterDropDown);
        Spinner sizeSpinner = (Spinner) findViewById(R.id.ClassSizeDropDown);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.Year, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> quarterAdapter = ArrayAdapter.createFromResource(this,
                R.array.Quarter, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.Class_Size, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        quarterSpinner.setAdapter(quarterAdapter);
        sizeSpinner.setAdapter(sizeAdapter);
        yearSpinner.setOnItemSelectedListener(this);
        quarterSpinner.setOnItemSelectedListener(this);
        sizeSpinner.setOnItemSelectedListener(this);

        //Show classes

        RecyclerView addedClasses = findViewById(R.id.classesRecyclerView);
        adapter = new ClassViewAdapter(false,true, courses, (course)-> {
            db.coursesDao().delete(course);
        });
        addedClasses.setAdapter(adapter);
        addedClasses.setLayoutManager(new LinearLayoutManager(this));

    }

    //Store selected year, quarter and size
    public void onItemSelected(AdapterView<?> parent, View view,
                                int pos, long id) {
        if(parent.getId() == R.id.YearDropDown)
            selectedYear = (String) parent.getItemAtPosition(pos);
        if(parent.getId() == R.id.QuarterDropDown)
            selectedQuarter = (String) parent.getItemAtPosition(pos);
        if(parent.getId() == R.id.ClassSizeDropDown) {
            selectedSize = (String) parent.getItemAtPosition(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Add class to database
    public void addClassOnClicked(View view) {
        int newNodeId = db.coursesDao().count() + 1;
        String personId = person.getId();
        TextView subjectView = findViewById(R.id.editSubject);
        TextView courseView = findViewById(R.id.editCourse);
        if(subjectView.length() == 0 || courseView.length() == 0)
        {
            Toast.makeText(this, "No Empty Entries!",Toast.LENGTH_SHORT).show();
        } else {
            subject = subjectView.getText().toString().toUpperCase(Locale.ROOT);
            course = courseView.getText().toString();
            Class newClass = new Class( selectedYear, selectedQuarter, subject, course, selectedSize);
            String classInfo = newClass.toData();
            Courses newCourse = new Courses(newNodeId,personId, classInfo);
            db.coursesDao().insert(newCourse);
            adapter.addClass(newCourse);
            courseView.setText("");
        }
    }

    //Go to search bof page
    public void doneonClick(View view) {
        Intent intent = new Intent(this, searchingActivity.class);
        intent.putExtra("user_id", personId);
        startActivity(intent);
    }

    public void classListOnClick(View view) {
        Intent intent = new Intent(this, ClassListActivity.class);
        intent.putExtra("user_id", personId);
        startActivity(intent);
    }


    public class Class {
        public String year, quarter, subject, course, size;

        public Class(String yr, String qt, String sj, String cour, String sz) {
            year = yr;
            quarter = qt;
            subject = sj;
            course = cour;
            size = sz;
        }

        public String getYear() { return year; }
        public String getQuarter() { return quarter; }
        public String getSubject() { return subject; }
        public String getCourse() { return course; }
        public String getSize() { return size; }

        //Return formatted class
        public String toData() {
            return this.year + " " + this.quarter + " " +
                    this.subject + " " + this.course + " " + this.size;
        }

    }
}