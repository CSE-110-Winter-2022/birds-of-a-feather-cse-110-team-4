package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.birdsoffeather.model.IPerson;
import com.example.birdsoffeather.model.db.AppDatabase;

public class profileActivity extends AppCompatActivity {
    private AppDatabase db;
    private IPerson person;
    private int personId = 0;
    private  String name;
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

    }
}