package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Courses;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class studentInfo extends AppCompatActivity {
    //Recycler View
    private RecyclerView classesRecyclerView;
    private RecyclerView.LayoutManager classesRecyclerViewManager;
    //Data base
    private AppDatabase db;
    private IPerson person;
    private int personId;
    private  String name;
    private List<Courses> courses;
    private String imageURL;

    //Adapter
    private ClassViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Intent intent = getIntent();
        personId = intent.getIntExtra( "person_id",1);
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);

        //Getting data from data base (db)
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        courses = db.coursesDao().gerForPerson(personId);
        imageURL = person.getURL();
        TextView nameView = findViewById(R.id.studentName);
        nameView.setText(name);//Set name for user

        //Adapter View
        classesRecyclerView = (RecyclerView) findViewById(R.id.studentClassList);
        adapter = new ClassViewAdapter(false, courses, (course)-> {
            //db.coursesDao().delete(course);
        });
        classesRecyclerView.setAdapter(adapter);
        classesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Displaying image
        ImageView i = (ImageView) findViewById(R.id.profile_picture_view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView i = (ImageView)findViewById(R.id.profile_picture_view);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            i.setImageBitmap(bitmap);

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //Displaying courses
        //TextView courseView = findViewById(R.id.studentClassList);
        //courseView.setText(course.toString());

    }

    public void backToList(View view) {
        finish();
    }
}