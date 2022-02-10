package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdsoffeather.model.db.Courses;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MockCSVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_csvactivity);
    }

    public void onAddStudentClicked(View view) {
        String name, url;
        List<String> courses = new ArrayList<String>();

        TextView infoView = findViewById(R.id.studentInfo);
        String info = infoView.getText().toString();
        if(!TextUtils.isEmpty(info)) {
            String[] lines = info.split(System.getProperty("line.separator"));
            name = lines[0].split(",")[0];
            url = lines[1].split(",")[0];
            for(int i = 2; i < lines.length; i++) {
                String[] c = lines[i].split(",");
                String newCourse = c[0]+" "+c[1]+" "+c[2]+" "+c[3];
                courses.add(newCourse);
            }

            //for testing
            TextView nView = findViewById(R.id.name);
            TextView uView = findViewById(R.id.url);
            TextView yView = findViewById(R.id.yr);
            nView.setText(name);
            uView.setText(url);
            yView.setText(courses.get(0));

        } else {
            Toast.makeText(this, "No Empty Entries!",Toast.LENGTH_SHORT).show();
        }


    }

    public void onGoToBoFClicked(View view) {
    }
}