package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class studentInfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        TextView personNameView = findViewById(R.id.studentName);

        Intent intent =getIntent();

        String personName = intent.getStringExtra("Person_Name");
        String [] personNotes = intent.getStringArrayExtra("Class_list");

        setTitle(personName);
        personNameView.setText(String.join("\n", personName));
    }

    public void backToList(View view) {
        finish();
    }
}