package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;
import com.example.birdsoffeather.model.db.Person;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class profileActivity extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person;
    private int personId = 0;
    private  String name;
    private TextView nameView;
    ImageView i;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialize database and get user's information from database when the page is created
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        nameView = findViewById(R.id.editTextTextPersonName);
        nameView.setText(name);
        String URL = person.getURL();

        //use Async function to let loading from URL runs on the background
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    i = (ImageView)findViewById(R.id.profile_picture_view);
                    bitmap = BitmapFactory.decodeStream((InputStream)new URL(URL).getContent());
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

    public void SubmitonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        //passing the person_id of the user into next page
        intent.putExtra("person_id", personId);
        startActivity(intent);
    }

    public void saveonClick(View view) {
        //get URL from textView
        TextView urlView = findViewById(R.id.image_url_input);
        String url = urlView.getText().toString();
        //get image from imageView
        i = (ImageView)findViewById(R.id.profile_picture_view);
        //get name from textView
        name = nameView.getText().toString();

        //use Async function to let loading from URL runs on the background
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    i = (ImageView)findViewById(R.id.profile_picture_view);
                    bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
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

        //Update information
        Person newPerson = new Person(0,name,url);
        db.personsWithCoursesDao().update(newPerson);
    }

}