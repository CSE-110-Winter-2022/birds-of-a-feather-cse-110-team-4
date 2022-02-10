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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class profileActivity extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person;
    private int personId = 0;
    private  String name;
    private Handler handler;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = AppDatabase.singleton(this);
        person = db.personsWithCoursesDao().get(personId);
        name = person.getName();
        TextView nameView = findViewById(R.id.editTextTextPersonName);
        nameView.setText(name);
    }

    public void SubmitonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        intent.putExtra("person_id", personId);
        startActivity(intent);
    }

    public void saveonClick(View view) {
        TextView urlView = findViewById(R.id.image_url_input);
        String url = urlView.getText().toString();
        ImageView i = (ImageView)findViewById(R.id.profile_picture_view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView i = (ImageView)findViewById(R.id.profile_picture_view);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
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

}