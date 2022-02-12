package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class studentInfo extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person;
    private int personId;
    private  String name;
    private List course;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        Intent intent = getIntent();
        personId = intent.getIntExtra( "Person_id",1);
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);

        //Getting data from data base (db)
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        course = person.getCourses();
        imageURL = person.getURL();
        TextView nameView = findViewById(R.id.studentName);
        nameView.setText(name);//Set name for user
        //TextView courseView = findViewById(R.id.course);
        //courseView.setText(course.toString());


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
    }

    public void backToList(View view) {
        finish();
    }
}